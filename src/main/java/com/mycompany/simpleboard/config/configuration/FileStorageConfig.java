package com.mycompany.simpleboard.config.configuration;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig {
    private final Path uploadDir = Paths.get("src/main/resources/static/uploads");

    public Path getUploadDir() {
        return uploadDir;
    }
}
