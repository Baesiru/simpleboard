package com.mycompany.simpleboard.controller;

import com.mycompany.simpleboard.config.interceptor.Auth;
import com.mycompany.simpleboard.dto.board.BoardRequest;
import com.mycompany.simpleboard.dto.board.BoardResponse;
import com.mycompany.simpleboard.service.BoardService;
import com.mycompany.simpleboard.service.RedisService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping()
    @Auth
    public ResponseEntity<Object> createBoard(@RequestPart("board") @Valid BoardRequest boardRequest,
                                              @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                              HttpSession session) {
        boardService.create(boardRequest, images, session);
        return ResponseEntity.ok().body("게시글이 작성되었습니다.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> modifyBoard(@PathVariable Long id,
                                              @RequestPart("board") @Valid BoardRequest boardRequest,
                                              @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                              HttpSession httpSession) {
        boardService.modify(id, boardRequest, images, httpSession);
        return ResponseEntity.ok().body("게시글이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBoard(@PathVariable Long id,
                                              HttpSession httpSession) {
        boardService.delete(id, httpSession);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBoard(@PathVariable Long id) {
        BoardResponse boardResponse = boardService.read(id);
        return ResponseEntity.ok().body(boardResponse);
    }

    @GetMapping()
    public ResponseEntity<Object> getBoards(@RequestParam Integer page) {
        Map<String, Object> map = boardService.readPage(page);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/search-title")
    public ResponseEntity<Object> findBoardsByTitle(@RequestParam String title, @RequestParam Integer page) {
        Map<String, Object> map = boardService.findLikeTitle(title, page);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/search-content")
    public ResponseEntity<Object> findBoardsByContent(@RequestParam String content, @RequestParam Integer page) {
        Map<String, Object> map = boardService.findLikeContent(content, page);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/search-username")
    public ResponseEntity<Object> findBoardsByUsername(@RequestParam String username, @RequestParam Integer page) {
        Map<String, Object> map = boardService.findLikeUsername(username, page);
        return ResponseEntity.ok().body(map);
    }

}
