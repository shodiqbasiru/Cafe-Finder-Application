package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.CafeOwner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository{

    void insert(CafeOwner owner);
    Optional<CafeOwner> findOwnerById(String id);
    List<CafeOwner> findAllOwners();
    void update(CafeOwner owner);
}

/*    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_cafe_owner(id, email, owner_name, number_phone, account_id) VALUES (" +
                    ":#{#owner.id}, " +
                    ":#{#owner.email}," +
                    ":#{#owner.name}," +
                    ":#{owner.numberPhone}," +
                    ":#{owner.account.id} " +
                    ")"
    )
    void insert(CafeOwner owner);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_cafe_owner WHERE id = :id")
    Optional<CafeOwner> findOwnerById(String id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_cafe_owner")
    List<CafeOwner> findAllOwners();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "UPDATE tb_cafe_owner SET " +
            "email = :#{#owner.email}," +
            "owner_name = :#{#owner.name}," +
            "number_phone = :#{#owner.numberPhone} " +
            "WHERE id = :#{#owner.id}")
    void update(CafeOwner owner);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "DELETE FROM tb_cafe_owner WHERE id = :id")
    void deleteCafeOwnerById(String id); // not in use, because implement soft delete*/

