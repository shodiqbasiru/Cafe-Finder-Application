package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository{
    void insert(User user);
    Optional<User> findUserById(String id);
    List<User> findAllUsers();
    void update(User user);

/*    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "INSERT INTO tb_regular_user(id, name, number_phone, account_id) VALUES (" +
            ":#{#user.id}, " +
            ":#{user.name}, " +
            ":#{user.numberPhone}, " +
            ":#{user.account.id} " +
            ")"
    )
    void insert(User user);

    @Query(nativeQuery = true,
    value = "SELECT * FROM tb_regular_user WHERE id = :id")
    Optional<User> findUserById(String id);

    @Query(nativeQuery = true,
    value = "SELECT * FROM tb_regular_user")
    List<User> findAllUsers();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "UPDATE tb_regular_user SET " +
            "name = :#{#user.name}, " +
            "number_phone = :#{#user.numberPhone} " +
            "WHERE id = :#{#user.id}")
    void update(User user);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "DELETE FROM tb_regular_user WHERE id = :id")
    void deleteUserById(String id); // not in use, because implement soft delete*/
}
