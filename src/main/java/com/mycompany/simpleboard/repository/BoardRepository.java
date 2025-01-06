package com.mycompany.simpleboard.repository;

import com.mycompany.simpleboard.dto.board.PageResponse;
import com.mycompany.simpleboard.entity.Board;
import com.mycompany.simpleboard.entity.enums.BoardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainsAndStatusNot(String title, BoardStatus status, Pageable pageable);
    Page<Board> findByContentContainsAndStatusNot(String content, BoardStatus status, Pageable pageable);
    Page<Board> findByUsernameContainsAndStatusNot(String username, BoardStatus status, Pageable pageable);
    boolean existsByIdAndStatusNot(Long id, BoardStatus status);
}
