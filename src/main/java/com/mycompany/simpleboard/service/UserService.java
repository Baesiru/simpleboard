package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.exception.user.*;
import com.mycompany.simpleboard.dto.user.management.*;
import com.mycompany.simpleboard.dto.user.login.LoginRequest;
import com.mycompany.simpleboard.dto.user.register.RegisterRequest;
import com.mycompany.simpleboard.entity.User;
import com.mycompany.simpleboard.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void existsByUsername(String username) {
        boolean user = userRepository.existsByUsername(username);
        if (user) {
            throw new ExistsUsernameException(UserErrorCode.EXISTS_USER_NAME);
        }
    }

    @Transactional(readOnly = true)
    public void existsByEmail(String email) {
        boolean user = userRepository.existsByEmail(email);
        if (user) {
            throw new ExistsEmailException(UserErrorCode.EXISTS_USER_EMAIL);
        }
    }

    @Transactional
    public void register(RegisterRequest registerRequest) {
        existsByEmail(registerRequest.getEmail());
        existsByUsername(registerRequest.getUsername());

        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public String login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailException(UserErrorCode.LOGIN_FAIL);
        }

        user.setLastLoginAt(LocalDateTime.now());
        return username;
    }

    @Transactional(readOnly = true)
    public FindUsernameResponse findUsername(FindUsernameRequest findUsernameRequest) {
        String email = findUsernameRequest.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));
        FindUsernameResponse findUsernameResponse = new FindUsernameResponse();
        findUsernameResponse.setUsername(user.getUsername());
        return findUsernameResponse;
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest, HttpSession httpSession) {
        String username = httpSession.getAttribute("username").toString();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));
        String currPassword = changePasswordRequest.getCurrPassword();
        String changedPassword = changePasswordRequest.getChangedPassword();

        // 저장된 비밀번호와 입력한 현재 비밀번호가 다를 때
        if (!passwordEncoder.matches(currPassword, user.getPassword())) {
            throw new LoginFailException(UserErrorCode.LOGIN_FAIL);
        }
        // 입력한 현재 비밀번호와 바꿀 비밀번호가 같을 때
        if (currPassword.equals(changedPassword)) {
            throw new SamePasswordException(UserErrorCode.SAME_PASSWORD_ERROR);
        }
        // 저장된 비밀번호와 바꿀 비밀번호가 같을 때
        if (passwordEncoder.matches(changedPassword, user.getPassword())) {
            throw new SamePasswordException(UserErrorCode.SAME_PASSWORD_ERROR);
        }

        user.setPassword(passwordEncoder.encode(changedPassword));
        userRepository.save(user);
    }

    @Transactional
    public void checkUserAndSendEmail(CheckUsernameAndEmailRequest checkUsernameAndEmailRequest){
        String username = checkUsernameAndEmailRequest.getUsername();
        String email = checkUsernameAndEmailRequest.getEmail();
        String code = makeCode();
        checkUser(username, email);
        sendEmail(email, code);
        putCodeInRedis(email, code);
    }

    @Transactional(readOnly = true)
    public void checkUser(String username, String email) {
        userRepository.findByUsernameAndEmail(username, email)
                .orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    public void sendEmail(String email, String code) {
        emailService.sendMail(email, code);
    }

    public void putCodeInRedis(String email, String code) {
        if (redisService.existData(email)) {
            redisService.deleteData(email);
            redisService.setDataExpire(email, code, 60 * 5L);
        } else {
            redisService.setDataExpire(email, code, 60 * 5L);
        }
    }

    public String makeCode(){
        Random random = new Random();
        return String.valueOf(random.nextInt(888888) + 111111);
    }

    @Transactional
    public void checkEmailAndCode(CheckEmailAndCodeRequest checkEmailAndCodeRequest) {
        String email = checkEmailAndCodeRequest.getEmail();
        String code = checkEmailAndCodeRequest.getCode();
        String redisCode = redisService.getData(email);
        if (redisCode.equals(code)) {
            redisService.deleteData(email);
        }
        else {
            throw new CodeNotEqualsException(UserErrorCode.CODE_NOT_EQUALS);
        }
    }

    @Transactional
    public void changeNewPassword(ChangeNewPasswordRequest changeNewPasswordRequest) {
        String username = changeNewPasswordRequest.getUsername();
        String password = changeNewPasswordRequest.getPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new SamePasswordException(UserErrorCode.SAME_PASSWORD_ERROR);
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
