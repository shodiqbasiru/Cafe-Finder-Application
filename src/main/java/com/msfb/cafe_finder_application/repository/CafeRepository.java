package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, String>, JpaSpecificationExecutor<Cafe>{
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_cafe VALUES" +
                    "(" +
                    ":#{#cafe.id}, " +
                    ":#{#cafe.address}, " +
                    ":#{#cafe.cafeName}, " +
                    ":#{#cafe.location}, " +
                    ":#{#cafe.phoneNumber}, " +
                    ":#{#cafe.urlLocation}" +
                    ")"
    )
    void insert(Cafe cafe);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM tb_cafe WHERE id = :id")
    Optional<Cafe> findCafeById(@Param("id") String id);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_cafe WHERE cafe_name LIKE CONCAT('%', :cafeName, '%')")
    List<Cafe> findAllByCafeName(@Param("cafeName") String cafeName);

    // update
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE tb_cafe SET " +
                    "address = :#{#cafe.address}, " +
                    "cafe_name = :#{#cafe.cafeName}, " +
                    "location = :#{#cafe.location}, " +
                    "phone_number = :#{#cafe.phoneNumber}, " +
                    "url_location = :#{#cafe.urlLocation} " +
                    "WHERE id = :#{#cafe.id}"
    )
    void updateCafe(@Param("cafe") Cafe cafe);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM tb_cafe WHERE id = :id")
    void deleteMenuById(@Param("id") String id);
}
