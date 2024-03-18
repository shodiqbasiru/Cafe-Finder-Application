package com.msfb.cafe_finder_application.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String id;
    private String username;
    private Boolean isAccountActive;
    private List<String> roles;
}
