package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMenuRequest {
    private String id;
    private String menuName;
    private Long price;
    private String description;
}
