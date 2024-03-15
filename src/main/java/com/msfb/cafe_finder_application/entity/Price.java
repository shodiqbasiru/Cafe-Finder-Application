package com.msfb.cafe_finder_application.entity;

import com.msfb.cafe_finder_application.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = TableConstant.PRICE_TABLE)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToOne
    @JoinColumn (name = "menu_id", unique = true, nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn (name = "cafe_id", nullable = false)
    private Cafe cafe;
}
