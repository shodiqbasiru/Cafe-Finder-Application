package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    @Query(nativeQuery = true,
    value = "SELECT * FROM tb_account WHERE id = :id")
    Optional<Account> findUserById(String id);

    @Query(nativeQuery = true,
    value = "SELECT * FROM tb_account WHERE username = :username")
    Optional<Account> findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "INSERT INTO tb_account VALUES (" +
            ":#{#account.id}, " +
            ":#{#account.isEnable}, " +
            ":#{#account.name}, " +
            ":#{#account.password}, " +
            ":#{#account.username}" +
            ")"
    )
    void insert(Account account);
}
