package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.AuthRequest;
import com.msfb.cafe_finder_application.dto.response.LoginResponse;
import com.msfb.cafe_finder_application.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerOwner(AuthRequest request);
    RegisterResponse registerUser(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
