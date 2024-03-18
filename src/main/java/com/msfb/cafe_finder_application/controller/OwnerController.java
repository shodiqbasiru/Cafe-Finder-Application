package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.constant.RouteApi;
import com.msfb.cafe_finder_application.dto.request.UpdateOwnerRequest;
import com.msfb.cafe_finder_application.dto.response.AccountResponse;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.dto.response.OwnerResponse;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.service.AccountService;
import com.msfb.cafe_finder_application.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApi.OWNER_API)
public class OwnerController {
    private final OwnerService authService;

    @PreAuthorize("hasAnyRole('OWNER_CAFE','ADMIN') ")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<OwnerResponse>> getById(@PathVariable String id) {

        CafeOwner owner = authService.getById(id);
        Account account = owner.getAccount();
        List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        AccountResponse accountResponse = AccountResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .roles(roles)
                .isAccountActive(account.getIsEnable())
                .build();

        OwnerResponse ownerResponse = OwnerResponse.builder()
                .id(owner.getId())
                .ownerName(owner.getName())
                .phoneNumber(owner.getNumberPhone())
                .accountDetails(accountResponse)
                .build();

        CommonResponse<OwnerResponse> response = CommonResponse.<OwnerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ownerResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('OWNER_CAFE','ADMIN') ")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<OwnerResponse>>> getAll() {
        List<CafeOwner> result = authService.getAllData();

        List<OwnerResponse> ownerResponses = result.stream()
                .map(owner -> {
                    Account account = owner.getAccount();
                    List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                    AccountResponse accountResponse = AccountResponse.builder()
                            .id(account.getId())
                            .username(account.getUsername())
                            .roles(roles)
                            .isAccountActive(account.getIsEnable())
                            .build();

                    return OwnerResponse.builder()
                            .id(owner.getId())
                            .ownerName(owner.getName())
                            .phoneNumber(owner.getNumberPhone())
                            .accountDetails(accountResponse)
                            .build();
                })
                .toList();

        CommonResponse<List<OwnerResponse>> response = CommonResponse.<List<OwnerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ownerResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('OWNER_CAFE','ADMIN') ")
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<OwnerResponse>> update(@RequestBody UpdateOwnerRequest request) {
        authService.update(request);

        CommonResponse<OwnerResponse> response = CommonResponse.<OwnerResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Updated data successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('OWNER_CAFE','ADMIN') ")
    @DeleteMapping(
            path = "/{id}"
    )
    public ResponseEntity<CommonResponse<CafeOwner>> delete(@PathVariable String id) {
        authService.delete(id);
        CommonResponse<CafeOwner> response = CommonResponse.<CafeOwner>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deleted data successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
