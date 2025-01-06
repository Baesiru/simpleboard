package com.mycompany.simpleboard.controller;

import com.mycompany.simpleboard.config.interceptor.Auth;
import com.mycompany.simpleboard.dto.comment.CommentRequest;
import com.mycompany.simpleboard.dto.comment.CommentResponse;
import com.mycompany.simpleboard.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping()
    @Auth
    public ResponseEntity<Object> createComment(@RequestBody CommentRequest commentRequest,
                                                HttpSession httpSession) {
        commentService.create(commentRequest, httpSession);
        return ResponseEntity.ok().body("댓글이 작성되었습니다.");
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Object> getComments(@PathVariable Long boardId){
        List<CommentResponse> comments = commentService.getComments(boardId);
        return ResponseEntity.ok().body(comments);
    }
}
