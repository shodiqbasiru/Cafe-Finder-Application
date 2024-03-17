package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.constant.RouteApi;
import com.msfb.cafe_finder_application.dto.request.ReviewRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateReviewRequest;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.dto.response.ReviewResponse;
import com.msfb.cafe_finder_application.entity.Review;
import com.msfb.cafe_finder_application.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApi.REVIEW_API)
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Review>> insert(@RequestBody ReviewRequest review) {
        reviewService.insert(review);

        CommonResponse<Review> response = CommonResponse.<Review>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Review created successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<ReviewResponse>>> getAll() {
        List<ReviewResponse> review = reviewService.getAll();
        CommonResponse<List<ReviewResponse>> response = CommonResponse.<List<ReviewResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Review found")
                .data(review)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Review>> update(@RequestBody UpdateReviewRequest request) {
        reviewService.update(request);
        CommonResponse<Review> response = CommonResponse.<Review>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Review updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Review>> deleteById(@PathVariable String id) {
        reviewService.deleteById(id);
        CommonResponse<Review> response = CommonResponse.<Review>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Review deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

}
