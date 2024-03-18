package com.msfb.cafe_finder_application.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String phoneNumber;
    private AccountResponse accountDetails;
}
