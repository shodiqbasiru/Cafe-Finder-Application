package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.CafeRequest;
import com.msfb.cafe_finder_application.entity.Cafe;

public interface CafeService {
    Cafe createCafe(CafeRequest cafeRequest);
}
