package com.msfb.cafe_finder_application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msfb.cafe_finder_application.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = TableConstant.REVIEW_TABLE)
public class Review {
    @Id
    private String id;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_review", nullable = false, updatable = false)
    private Date dateReview;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;
}
