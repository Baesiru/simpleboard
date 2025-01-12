package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.exception.user.*;
import com.mycompany.simpleboard.dto.user.login.LoginRequest;
import com.mycompany.simpleboard.dto.user.register.RegisterRequest;
import com.mycompany.simpleboard.entity.User;
import com.mycompany.simpleboard.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
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
}
