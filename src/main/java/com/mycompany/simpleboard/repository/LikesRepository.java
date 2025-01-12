package com.mycompany.simpleboard.repository;

import com.mycompany.simpleboard.entity.Board;
import com.mycompany.simpleboard.entity.Likes;
import com.mycompany.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndBoard(User user, Board board);
    Long countByBoard(Board board);
    boolean existsByUserAndBoard(User user, Board board);
}
