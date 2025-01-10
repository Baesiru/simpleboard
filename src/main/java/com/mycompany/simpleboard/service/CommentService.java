package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.exception.board.BoardErrorCode;
import com.mycompany.simpleboard.config.exception.board.BoardNotFoundException;
import com.mycompany.simpleboard.config.exception.comment.CommentErrorCode;
import com.mycompany.simpleboard.config.exception.comment.CommentNotFoundException;
import com.mycompany.simpleboard.config.exception.user.LoginFailException;
import com.mycompany.simpleboard.config.exception.user.UserErrorCode;
import com.mycompany.simpleboard.dto.comment.CommentRequest;
import com.mycompany.simpleboard.dto.comment.CommentResponse;
import com.mycompany.simpleboard.entity.Comment;
import com.mycompany.simpleboard.entity.enums.BoardStatus;
import com.mycompany.simpleboard.entity.enums.CommentStatus;
import com.mycompany.simpleboard.repository.BoardRepository;
import com.mycompany.simpleboard.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void create(CommentRequest commentRequest, HttpSession httpSession) {
        Comment comment = new Comment();

        //Comment comment = modelMapper.map(commentRequest, Comment.class);
        String username = getUsername(httpSession);
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setStatus(CommentStatus.CREATED);
        comment.setBoardId(commentRequest.getBoardId());

        //게시글이 존재하지 않거나 삭제 처리 되어 있을 시 예외 발생
        if (!boardRepository.existsByIdAndStatusNot(comment.getBoardId(), BoardStatus.DELETED)) {
            throw new BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND);
        }

        if (commentRequest.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentRequest.getParentId())
                    .orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
            comment.setParent(parentComment);
        }
        comment.setUsername(username);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getComments(Long boardId, int page) {
        Page<Comment> comments = commentRepository.findByBoardIdAndStatusNot(boardId, CommentStatus.DELETED, PageRequest.of(page, 100, Sort.by(Sort.Direction.DESC, "id")));
        Map<String, Object> map = new HashMap<>();
        map.put("boards", comments.getContent().stream().map(comment -> modelMapper.map(comment, CommentResponse.class)).toList());
        map.put("page", comments.getTotalPages());
        return map;
    }

    @Transactional
    public void modifyComment(Long id, CommentRequest commentRequest, HttpSession httpSession) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
        checkUsername(httpSession, comment);
        comment.setContent(commentRequest.getContent());
        comment.setStatus(CommentStatus.MODIFIED);
        comment.setModifiedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id, HttpSession httpSession) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
        checkUsername(httpSession, comment);
        comment.setStatus(CommentStatus.DELETED);
        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public String getUsername(HttpSession httpSession) {
        return httpSession.getAttribute("username").toString();
    }

    public void checkUsername(HttpSession httpSession, Comment comment) {
        String username = getUsername(httpSession);
        if (!username.equals(comment.getUsername())) {
            throw new LoginFailException(UserErrorCode.USER_NOT_MATCHED);
        }
    }
}
