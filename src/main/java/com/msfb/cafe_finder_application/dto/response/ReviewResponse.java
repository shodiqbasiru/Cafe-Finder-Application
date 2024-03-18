package com.msfb.cafe_finder_application.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private String id;
    private Double rating;
    private String comment;
    private String dateReview;
    private String cafeId;
    private String userId;
}
