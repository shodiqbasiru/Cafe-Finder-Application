package com.msfb.cafe_finder_application.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile image;
}
