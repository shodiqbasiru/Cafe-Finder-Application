package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewRequest {
    private String id;
    private Double rating;
    private String comment;
}
