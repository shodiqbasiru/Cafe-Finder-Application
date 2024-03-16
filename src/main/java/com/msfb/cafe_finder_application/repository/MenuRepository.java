package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>{
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_menu VALUES" +
                    "(" +
                    ":#{#menu.id}, " +
                    ":#{#menu.menuName}, " +
                    ":#{#menu.description}, " +
                    ":#{#menu.price}, " +
                    ":#{#menu.cafe.id}" +
                    ")"
    )
    void insert(Menu menu);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_menu WHERE id = :id")
    Optional<Menu> findMenuById(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_menu")
    List<Menu> findAllMenus();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tb_menu SET " +
            "menu_name = :#{#menu.menuName}, " +
            "price = :#{#menu.price}, " +
            "description = :#{#menu.description} " +
            "WHERE id = :#{#menu.id}")
    void updateMenu(Menu menu);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM tb_menu WHERE id = :id")
    void deleteMenuById(String id);
}
