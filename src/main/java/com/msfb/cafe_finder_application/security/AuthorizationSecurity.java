package com.msfb.cafe_finder_application.security;

import com.msfb.cafe_finder_application.dto.request.UpdateOwnerRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateUserRequest;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.service.AccountService;
import com.msfb.cafe_finder_application.service.OwnerService;
import com.msfb.cafe_finder_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("authorizeSecurity")
@RequiredArgsConstructor
public class AuthorizationSecurity {
    private final OwnerService ownerService;
    private final UserService userService;
    private final AccountService accountService;

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
