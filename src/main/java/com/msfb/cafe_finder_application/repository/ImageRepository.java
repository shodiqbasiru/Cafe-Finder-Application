package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
}
