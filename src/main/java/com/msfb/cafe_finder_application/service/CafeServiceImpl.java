package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.CafeRequest;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService{
    private final CafeRepository cafeRepository;
    @Override
    public Cafe createCafe(CafeRequest cafeRequest) {
        Cafe cafe = Cafe.builder()
                .cafeName(cafeRequest.getCafeName())
                .phoneNumber(cafeRequest.getPhoneNumber())
                .location(cafeRequest.getLocation())
                .address(cafeRequest.getAddress())
                .urlLocation(cafeRequest.getUrlLocation())
                .build();
        return cafeRepository.save(cafe);
    }
}
