package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image create(MultipartFile file);
    Resource getById(String id);
    void deleteById(String id);
}
