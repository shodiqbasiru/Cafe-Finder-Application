package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Account getByUserId(String id);
    Account getByContext();
}
