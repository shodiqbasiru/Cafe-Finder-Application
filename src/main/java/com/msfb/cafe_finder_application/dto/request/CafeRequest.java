package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CafeRequest {
    private String cafeName;
    private String phoneNumber;
    private String location;
    private String address;
    private String urlLocation;
}
