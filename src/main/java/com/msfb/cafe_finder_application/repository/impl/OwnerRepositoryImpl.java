package com.msfb.cafe_finder_application.repository.impl;

import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.repository.OwnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class OwnerRepositoryImpl implements OwnerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(CafeOwner owner) {
        entityManager.createNativeQuery("INSERT INTO tb_cafe_owner(id, email, owner_name, number_phone, account_id) VALUES (?, ?, ?, ?, ?)")
                .setParameter(1, owner.getId())
                .setParameter(2, owner.getEmail())
                .setParameter(3, owner.getName())
                .setParameter(4, owner.getNumberPhone())
                .setParameter(5, owner.getAccount().getId())
                .executeUpdate();
        entityManager.flush();
    }

    @Override
    public Optional<CafeOwner> findOwnerById(String id) {
        return entityManager.createNativeQuery("SELECT * FROM tb_cafe_owner WHERE id = ?", CafeOwner.class)
                .setParameter(1, id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<CafeOwner> findAllOwners() {
        return entityManager.createNativeQuery("SELECT * FROM tb_cafe_owner", CafeOwner.class)
                .getResultList();
    }

    @Override
    public void update(CafeOwner owner) {
        entityManager.createNativeQuery("UPDATE tb_cafe_owner SET email = ?, owner_name = ?, number_phone = ? WHERE id = ?")
                .setParameter(1, owner.getEmail())
                .setParameter(2, owner.getName())
                .setParameter(3, owner.getNumberPhone())
                .setParameter(4, owner.getId())
                .executeUpdate();
        entityManager.flush();
    }

}
