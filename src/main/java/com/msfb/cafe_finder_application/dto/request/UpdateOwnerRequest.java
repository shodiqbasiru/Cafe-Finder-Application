package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOwnerRequest {
    private String id;
    private String ownerName;
    private String email;
    private String numberPhone;
}
