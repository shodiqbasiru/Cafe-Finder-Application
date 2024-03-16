package com.msfb.cafe_finder_application.repository;

import com.msfb.cafe_finder_application.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String>{

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_review VALUES" +
                    "(" +
                    ":#{#review.id}," +
                    ":#{#review.comment}," +
                    ":#{#review.dateReview},"  +
                    ":#{#review.rating}," +
                    ":#{#review.cafe.id}" +
                    ")"
    )
   void insert(Review review);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_review WHERE id = :cafeId"
    )
    Optional<Review> findReviewById(String cafeId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_review"
    )
    List<Review> getAllData();

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE tb_review SET " +
                    "comment = :#{#review.comment}, " +
                    "date_review = :#{#review.dateReview}, " +
                    "rating = :#{#review.rating} " +
                    "WHERE id = :#{#review.id}"
    )
    void update(Review review);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_review WHERE id = :id"
    )
    void deleteReviewById(String id);
}
