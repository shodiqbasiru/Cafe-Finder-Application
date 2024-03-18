package com.msfb.cafe_finder_application.repository.impl;

import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(User user) {
        entityManager.createNativeQuery("INSERT INTO tb_regular_user(id, name, number_phone, account_id) VALUES (?,?, ?, ?)")
                .setParameter(1, user.getId())
                .setParameter(2, user.getName())
                .setParameter(3, user.getNumberPhone())
                .setParameter(4, user.getAccount().getId())
                .executeUpdate();
        entityManager.flush();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return entityManager.createNativeQuery("SELECT * FROM tb_regular_user WHERE id = ?", User.class)
                .setParameter(1, id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findAllUsers() {
        return entityManager.createNativeQuery("SELECT * FROM tb_regular_user", User.class)
                .getResultList();
    }

    @Override
    public void update(User user) {
        entityManager.createNativeQuery("UPDATE tb_regular_user SET name = ?, number_phone = ? WHERE id = ?")
                .setParameter(1, user.getName())
                .setParameter(2, user.getNumberPhone())
                .setParameter(3, user.getId())
                .executeUpdate();
        entityManager.flush();
    }
}
