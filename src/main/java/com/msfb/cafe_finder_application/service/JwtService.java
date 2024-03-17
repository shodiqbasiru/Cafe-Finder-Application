package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.JwtClaims;

public interface JwtService {
    String generateToken(Account account);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
