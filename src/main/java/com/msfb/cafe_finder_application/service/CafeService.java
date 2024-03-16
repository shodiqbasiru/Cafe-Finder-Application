package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.CafeRequest;
import com.msfb.cafe_finder_application.dto.request.PageCafeRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateCafeRequest;
import com.msfb.cafe_finder_application.entity.Cafe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CafeService {
    void createCafe(CafeRequest cafeRequest);
    Cafe getCafeById(String id);
    Page<Cafe> getAllCafes(PageCafeRequest request);
    List<Cafe> getCafeByCafeName(String cafeName);
    void updateCafe(UpdateCafeRequest cafeRequest);
    void deleteById(String id);
}
