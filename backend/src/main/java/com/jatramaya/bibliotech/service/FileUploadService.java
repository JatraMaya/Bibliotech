package com.jatramaya.bibliotech.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileUploadService {

    @Autowired
    private String uploadDirectory;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg");
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 800;
    private static final double QUALITY = 0.8;

    public String uploadAvatar(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Image cannot be large then 10MB.");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Acceptable file format can only be either JPG/JPEG, or PNG");
        }

        String originalFileName = file.getOriginalFilename();
        String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExt;

        Path filePath = Paths.get(uploadDirectory, newFileName);

        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        Thumbnails.of(originalImage)
                .size(MAX_WIDTH, MAX_HEIGHT)
                .outputQuality(QUALITY)
                .toFile(filePath.toFile());

        return "/uploads/avatars/" + newFileName;
    }

    public void deleteAvatar(String avatarUrl) throws IOException {
        if (avatarUrl != null && avatarUrl.startsWith("/uploads/avatars/")) {
            String filename = avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDirectory, filename);
            Files.deleteIfExists(filePath);
        }
    }

}
