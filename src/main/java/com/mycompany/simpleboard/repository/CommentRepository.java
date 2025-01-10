package com.mycompany.simpleboard.repository;

import com.mycompany.simpleboard.entity.Comment;
import com.mycompany.simpleboard.entity.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByBoardIdAndStatusNot(Long boardId, CommentStatus status, Pageable pageable);
}
