package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.configuration.FileStorageConfig;
import com.mycompany.simpleboard.entity.Board;
import com.mycompany.simpleboard.entity.Image;
import com.mycompany.simpleboard.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final Path uploadDir;
    private final ImageRepository imageRepository;

    public ImageService(FileStorageConfig config, ImageRepository imageRepository) {
        this.uploadDir = config.getUploadDir();
        this.imageRepository = imageRepository;

        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("업로드 폴더를 생성하는데 오류가 발생했습니다.", e);
        }
    }

    public List<Image> storeFiles(List<MultipartFile> images, Long boardId) {
        return images.stream().map(file -> {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path targetLocation = this.uploadDir.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                String fileUrl = uploadDir + fileName;
                Image image = imageFile(fileUrl, boardId);
                return imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("Could not store file " + file.getOriginalFilename(), e);
            }
        }).collect(Collectors.toList());
    }

    public Image imageFile(String fileUrl, Long boardId){
        Image image = new Image();
        image.setImageUrl(fileUrl);
        image.setBoardId(boardId);
        image.setCreatedAt(LocalDateTime.now());
        return image;
    }

    public List<Image> findImagesByBoardId(Long boardId) {
        return imageRepository.findByBoardId(boardId);
    }
}
