package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.constant.RouteApi;
import com.msfb.cafe_finder_application.dto.request.UpdateUserRequest;
import com.msfb.cafe_finder_application.dto.response.AccountResponse;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.dto.response.UserResponse;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApi.USER_API)
public class UserController {

    private final UserService userService;

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> getById(@PathVariable String id) {
        User user = userService.getById(id);
        Account account = user.getAccount();
        List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        AccountResponse accountResponse = AccountResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .roles(roles)
                .isAccountActive(account.getIsEnable())
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getAccount().getUsername())
                .phoneNumber(user.getNumberPhone())
                .accountDetails(accountResponse)
                .build();

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(userResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<UserResponse>>> getAll() {
        List<User> users = userService.getAllData();
        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    Account account = user.getAccount();
                    List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                    AccountResponse accountResponse = AccountResponse.builder()
                            .id(account.getId())
                            .username(account.getUsername())
                            .roles(roles)
                            .isAccountActive(account.getIsEnable())
                            .build();

                    return UserResponse.builder()
                            .id(user.getId())
                            .username(user.getAccount().getUsername())
                            .phoneNumber(user.getNumberPhone())
                            .accountDetails(accountResponse)
                            .build();
                })
                .toList();

        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(userResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> update(@RequestBody UpdateUserRequest request) {
        userService.update(request);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> delete(@PathVariable String id) {
        userService.delete(id);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(response);
    }

}
