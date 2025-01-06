package com.mycompany.simpleboard.repository;

import com.mycompany.simpleboard.entity.Comment;
import com.mycompany.simpleboard.entity.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment>  findByBoardIdAndStatusNot(Long boardId, CommentStatus status);
}
