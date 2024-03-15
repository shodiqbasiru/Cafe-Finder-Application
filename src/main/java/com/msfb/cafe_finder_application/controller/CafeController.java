package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.dto.request.CafeRequest;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cafes")
public class CafeController {
    private final CafeService cafeService;
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> create(@RequestBody CafeRequest request) {
        Cafe result = cafeService.createCafe(request);
        CommonResponse<Cafe> response = CommonResponse.<Cafe>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Created a new cafe successfully")
                .data(result)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
