package com.jatramaya.bibliotech.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfig {

    @Value("${FILE_UPLOAD_DIR}")
    private String uploadDir;

    @Bean
    public String uploadDirectory() {
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return uploadDir;
    }

}
