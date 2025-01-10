package com.mycompany.simpleboard.controller;

import com.mycompany.simpleboard.config.interceptor.Auth;
import com.mycompany.simpleboard.dto.comment.CommentRequest;
import com.mycompany.simpleboard.dto.comment.CommentResponse;
import com.mycompany.simpleboard.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> getComments(@PathVariable Long boardId, @RequestParam int page){
        Map<String, Object> map = commentService.getComments(boardId, page);
        return ResponseEntity.ok().body(map);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> modifyComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest,
                                                HttpSession httpSession) {
        commentService.modifyComment(id, commentRequest, httpSession);
        return ResponseEntity.ok().body("댓글이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id, HttpSession httpSession) {
        commentService.deleteComment(id, httpSession);
        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }
}
