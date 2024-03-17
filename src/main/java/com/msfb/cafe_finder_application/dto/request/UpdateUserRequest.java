package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String id;
    private String name;
    private String numberPhone;
}
