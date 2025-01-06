package com.mycompany.simpleboard.repository;

import com.mycompany.simpleboard.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBoardId(Long boardId);
}
