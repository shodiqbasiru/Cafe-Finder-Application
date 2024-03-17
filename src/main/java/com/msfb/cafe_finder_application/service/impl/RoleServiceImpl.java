package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.constant.RoleEnum;
import com.msfb.cafe_finder_application.entity.Role;
import com.msfb.cafe_finder_application.repository.RoleRepository;
import com.msfb.cafe_finder_application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findRole(RoleEnum role) {
        return roleRepository.findRoleByRole(role.name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "role not found"));
    }

    @Override
    public void createRole(Role role) {
        roleRepository.saveRole(role);
    }

    @Override
    public void createAccountRole(String accountId, String roleId) {
        roleRepository.saveRoleAccount(accountId, roleId);
    }

    @Override
    public Role getOrSave(RoleEnum role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(Role.builder().id(UUID.randomUUID().toString()).role(role).build()));
    }
}
