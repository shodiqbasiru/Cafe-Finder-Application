package com.msfb.cafe_finder_application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    private String menuName;
    private Long price;
    private String description;
    private String cafeId;

    @JsonIgnore
    private MultipartFile image;
}
