package com.msfb.cafe_finder_application.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String id;
    private String menuName;
    private Long price;
    private String description;
    private String cafeId;
}
