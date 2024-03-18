package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.dto.request.ReviewRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateReviewRequest;
import com.msfb.cafe_finder_application.dto.response.ReviewResponse;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.entity.Review;
import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.repository.ReviewRepository;
import com.msfb.cafe_finder_application.service.CafeService;
import com.msfb.cafe_finder_application.service.ReviewService;
import com.msfb.cafe_finder_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeService cafeService;
    private final UserService userService;

    @Override
    public void insert(ReviewRequest request) {
        Cafe cafe = cafeService.findCafeById(request.getCafeId());
        User user = userService.getById(request.getUserId());
        Review review = Review.builder()
                .id(UUID.randomUUID().toString())
                .cafe(cafe)
                .user(user)
                .rating(request.getRating())
                .comment(request.getComment())
                .dateReview(new Date())
                .build();
        reviewRepository.insert(review);
    }

    @Override
    public Review findReviewById(String id) {
        return reviewRepository.findReviewById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }

    @Override
    public List<ReviewResponse> getAll() {
        return reviewRepository.getAllData().stream()
                .map(review -> ReviewResponse.builder()
                        .id(review.getId())
                        .cafeId(review.getCafe().getId())
                        .userId(review.getUser().getId())
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .dateReview(review.getDateReview().toString())
                        .build()
                ).toList();
    }

    @Override
    public void update(UpdateReviewRequest request) {
        Review review = findReviewById(request.getId());

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setDateReview(new Date());

        reviewRepository.update(review);
    }

    @Override
    public void deleteById(String id) {
        Review review = findReviewById(id);
        reviewRepository.deleteReviewById(review.getId());
    }
}
