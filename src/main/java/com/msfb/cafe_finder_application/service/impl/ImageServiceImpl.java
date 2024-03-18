package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.entity.Image;
import com.msfb.cafe_finder_application.repository.ImageRepository;
import com.msfb.cafe_finder_application.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final Path directoryPath;

    public ImageServiceImpl(ImageRepository imageRepository, @Value("${cafe_finder.path_location}") String directoryPath) {
        this.imageRepository = imageRepository;
        this.directoryPath = Paths.get(directoryPath);
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Image create(MultipartFile file) {
        try {
            if (!List.of("image/jpeg", "image/jpg", "image/png").contains(file.getContentType()))
                throw new ConstraintViolationException("Invalid content type", null);
            String originalFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = directoryPath.resolve(originalFilename);
            Files.copy(file.getInputStream(), filePath);

            Image image = Image.builder()
                    .name(originalFilename)
                    .path(filePath.toString())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .build();

            return imageRepository.saveAndFlush(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   @Transactional(readOnly = true)
    @Override
    public Resource getById(String id) {
        try {
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new RuntimeException("Image not found");
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        try {
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new RuntimeException("Image not found");
            Files.delete(filePath);
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
