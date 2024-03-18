package com.msfb.cafe_finder_application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCafeRequest {
    private String id;
    private String cafeName;
    private String phoneNumber;
    private String location;
    private String address;
    private String urlLocation;

    @JsonIgnore
    private MultipartFile image;
}
