package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.constant.RoleEnum;
import com.msfb.cafe_finder_application.dto.request.AuthRequest;
import com.msfb.cafe_finder_application.dto.response.RegisterResponse;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.entity.Role;
import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.repository.AccountRepository;
import com.msfb.cafe_finder_application.service.AuthService;
import com.msfb.cafe_finder_application.service.OwnerService;
import com.msfb.cafe_finder_application.service.RoleService;
import com.msfb.cafe_finder_application.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final OwnerService ownerService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;



    @Value("${cafe_finder.username.admin}")
    private String adminUsername;
    @Value("${cafe_finder.password.admin}")
    private String adminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initialAdmin() {
        Optional<Account> username = accountRepository.findUserByUsername(adminUsername);
        if (username.isPresent()) return;

        Role admin = roleService.getOrSave(RoleEnum.ROLE_ADMIN);
        Role user = roleService.getOrSave(RoleEnum.ROLE_USER);
        Role owner = roleService.getOrSave(RoleEnum.ROLE_OWNER_CAFE);
        String hashPassword = passwordEncoder.encode(adminPassword);

        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .name("admin")
                .username(adminUsername)
                .password(hashPassword)
                .roles(List.of(admin, user, owner))
                .isEnable(true)
                .build();
        accountRepository.insert(account);

        roleService.createAccountRole(account.getId(), admin.getId());
        roleService.createAccountRole(account.getId(), user.getId());
        roleService.createAccountRole(account.getId(), owner.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerOwner(AuthRequest request) {
        Role role = roleService.getOrSave(RoleEnum.ROLE_OWNER_CAFE);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .isEnable(true)
                .name(request.getName())
                .password(hashPassword)
                .username(request.getUsername())
                .roles(List.of(role))
                .build();
        accountRepository.insert(account);

        CafeOwner cafeOwner = CafeOwner.builder()
                .id(UUID.randomUUID().toString())
                .name(account.getName())
                .account(account)
                .build();
        ownerService.create(cafeOwner);

        roleService.createAccountRole(account.getId(), role.getId());

        List<String> roles = account.getAuthorities().stream()
                .map((GrantedAuthority::getAuthority)).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerUser(AuthRequest request) {
        Role role = roleService.getOrSave(RoleEnum.ROLE_USER);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(role))
                .isEnable(true)
                .build();
        accountRepository.insert(account);
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(account.getName())
                .account(account)
                .build();
        userService.create(user);

        roleService.createAccountRole(account.getId(), role.getId());

        List<String> roles = account.getAuthorities().stream()
                .map((GrantedAuthority::getAuthority)).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }
}
