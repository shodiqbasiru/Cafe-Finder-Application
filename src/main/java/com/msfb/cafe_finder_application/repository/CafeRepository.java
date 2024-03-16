package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe,String>, JpaSpecificationExecutor<Cafe> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_cafe WHERE cafe_name LIKE CONCAT('%', :cafeName, '%')")
    List<Cafe> findAllByCafeName(@Param("cafeName") String cafeName);
}
