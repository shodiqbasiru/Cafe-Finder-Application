package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.constant.RoleEnum;
import com.msfb.cafe_finder_application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByRole(RoleEnum role);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_role WHERE role = :role")
    Optional<Role> findRoleByRole(String role);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_role VALUES" +
                    "(" +
                    ":#{#role.id}," +
                    ":#{#role.role}" +
                    ")"
    )
    void saveRole(Role role);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_account_roles(account_id, roles_id) VALUES (:accountId,:rolesId)")
    void saveRoleAccount(@Param("accountId") String accountId, @Param("rolesId") String rolesId);
}
