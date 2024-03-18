package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String cafeId;
    private Double rating;
    private String comment;
    private String userId;
}
