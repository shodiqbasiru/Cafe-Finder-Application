package com.msfb.cafe_finder_application.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msfb.cafe_finder_application.dto.request.UpdateCafeRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateOwnerRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateUserRequest;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.service.AccountService;
import com.msfb.cafe_finder_application.service.CafeService;
import com.msfb.cafe_finder_application.service.OwnerService;
import com.msfb.cafe_finder_application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("authorizeSecurity")
@RequiredArgsConstructor
@Slf4j
public class AuthorizationSecurity {
    private final OwnerService ownerService;
    private final UserService userService;
    private final CafeService cafeService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public boolean checkSameIdAsPrincipal(String json) throws JsonProcessingException {
        UpdateCafeRequest request = objectMapper.readValue(json, UpdateCafeRequest.class);
        log.info("request: {}", request.getId());
        Cafe cafe = cafeService.findCafeById(request.getId());
        String cafeOwnerId = cafe.getOwner().getAccount().getId();
        Account account = accountService.getByContext();
        log.info("account: {}", account.getId());
        log.info("cafeOwnerId: {}", cafeOwnerId);
        if (!account.getId().equals(cafeOwnerId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot access this resource");
        }
        return true;
    }

    public boolean checkSameIdAsPrincipal(UpdateOwnerRequest request){
        CafeOwner owner = ownerService.getById(request.getId());
        Account account = accountService.getByContext();

        if (!account.getId().equals(owner.getAccount().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot access this resource");
        }
        return true;
    }

    public boolean checkSameIdAsPrincipal(UpdateUserRequest request){
        User user = userService.getById(request.getId());
        Account account = accountService.getByContext();

        if (!account.getId().equals(user.getAccount().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot access this resource");
        }
        return true;
    }
}
