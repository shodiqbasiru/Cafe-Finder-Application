package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    private String name;
    private String username;
    private String password;
}
