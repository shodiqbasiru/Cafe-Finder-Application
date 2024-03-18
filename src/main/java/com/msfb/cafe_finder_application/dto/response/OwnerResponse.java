package com.msfb.cafe_finder_application.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerResponse {
    private String id;
    private String ownerName;
    private String phoneNumber;
    private AccountResponse accountDetails;
}
