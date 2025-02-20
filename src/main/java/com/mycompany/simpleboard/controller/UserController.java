package com.mycompany.simpleboard.controller;

import com.mycompany.simpleboard.config.interceptor.Auth;
import com.mycompany.simpleboard.dto.user.duplication.DuplicationEmailRequest;
import com.mycompany.simpleboard.dto.user.duplication.DuplicationUsernameRequest;
import com.mycompany.simpleboard.dto.user.management.*;
import com.mycompany.simpleboard.dto.user.login.LoginRequest;
import com.mycompany.simpleboard.dto.user.register.RegisterRequest;
import com.mycompany.simpleboard.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest registerRequest){
        userService.register(registerRequest);
        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/duplication/username")
    public ResponseEntity<Object> validateUsername(@RequestBody @Valid DuplicationUsernameRequest duplicationUsernameRequest){
        userService.existsByUsername(duplicationUsernameRequest.getUsername());
        return ResponseEntity.status(200).body("사용 가능한 아이디입니다.");
    }

    @PostMapping("/duplication/email")
    public ResponseEntity<Object> validateEmail(@RequestBody @Valid DuplicationEmailRequest duplicationEmailRequest){
        userService.existsByEmail(duplicationEmailRequest.getEmail());
        return ResponseEntity.status(200).body("사용 가능한 이메일입니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession){
        String username = userService.login(loginRequest);
        httpSession.setAttribute("username", username);
        return ResponseEntity.status(200).body(username + "님 환영합니다.");
    }

    @GetMapping("/session")
    public ResponseEntity<Object> getSession(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        return ResponseEntity.ok().body(username + " session id = " + httpSession.getId());
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession httpSession, HttpServletResponse response){
        httpSession.invalidate();
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().body("정상적으로 로그아웃 되었습니다.");
    }

    @PostMapping("/find/username")
    public ResponseEntity<Object> findUsername(@RequestBody @Valid FindUsernameRequest findUsernameRequest){
        FindUsernameResponse findUsernameResponse = userService.findUsername(findUsernameRequest);
        return ResponseEntity.ok().body(findUsernameResponse);
    }

    @PostMapping("/change/password")
    @Auth
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest, httpSession);
        return ResponseEntity.ok().body("정상적으로 비밀번호 변경이 완료되었습니다.");
    }

    @PostMapping("/send/email")
    public ResponseEntity<Object> checkUserAndSendEmail(@RequestBody @Valid CheckUsernameAndEmailRequest checkUsernameAndEmailRequest) {
        userService.checkUserAndSendEmail(checkUsernameAndEmailRequest);
        return ResponseEntity.ok().body("이메일을 정상적으로 전송하였습니다.");
    }


    @PostMapping("/check/code")
    public ResponseEntity<Object> checkCode(@RequestBody @Valid CheckEmailAndCodeRequest checkEmailAndCodeRequest) {
        userService.checkEmailAndCode(checkEmailAndCodeRequest);
        return ResponseEntity.ok().body("인증번호가 일치합니다.");
    }

}
