package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.constant.RoleEnum;
import com.msfb.cafe_finder_application.entity.Role;

public interface RoleService {
    Role findRole(RoleEnum role);
    void createRole(Role role);
    void createAccountRole(String accountId, String roleId);

    Role getOrSave(RoleEnum role);
}
