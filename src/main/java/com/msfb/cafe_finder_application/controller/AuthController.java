package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.constant.RouteApi;
import com.msfb.cafe_finder_application.dto.request.AuthRequest;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.dto.response.RegisterResponse;
import com.msfb.cafe_finder_application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApi.AUTH_API)
public class AuthController {
    private final AuthService authService;

    @PostMapping(
            path = "/register-owner",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RegisterResponse>> registerOwner(@RequestBody AuthRequest request) {
        RegisterResponse registerResponse = authService.registerOwner(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Owner registered successfully")
                .data(registerResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            path = "/register-user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RegisterResponse>> registerUser(@RequestBody AuthRequest request) {
        RegisterResponse registerResponse = authService.registerUser(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User registered successfully")
                .data(registerResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
