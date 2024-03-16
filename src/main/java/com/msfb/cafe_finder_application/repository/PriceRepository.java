package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceService extends JpaRepository<Price, String> {
}
