package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.ReviewRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateReviewRequest;
import com.msfb.cafe_finder_application.dto.response.ReviewResponse;
import com.msfb.cafe_finder_application.entity.Review;

import java.util.List;

public interface ReviewService {
    void insert(ReviewRequest review);
    Review findReviewById(String id);
    List<ReviewResponse> getAll();
    void update(UpdateReviewRequest request);
    void deleteById(String id);
}
