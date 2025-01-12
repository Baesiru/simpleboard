package com.mycompany.simpleboard.controller;

import com.mycompany.simpleboard.config.interceptor.Auth;
import com.mycompany.simpleboard.service.LikesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LikeController {
    @Autowired
    private LikesService likesService;

    @PostMapping("/like/{boardId}")
    @Auth
    public ResponseEntity<Object> like(@PathVariable Long boardId, HttpSession httpSession) {
        likesService.like(boardId, httpSession);
        return ResponseEntity.ok().body("추천이 완료되었습니다.");
    }

    @PostMapping("/unlike/{boardid}")
    @Auth
    public ResponseEntity<Object> unlike(@PathVariable Long boardid, HttpSession httpSession) {
        likesService.unlike(boardid, httpSession);
        return ResponseEntity.ok().body("추천이 취소되었습니다.");
    }
}
