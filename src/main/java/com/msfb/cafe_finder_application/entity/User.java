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
@Table(name = TableConstant.REGULAR_USER_TABLE)
public class User {
    @Id
    private String id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "number_phone", length = 15, unique = true)
    private String numberPhone;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;
}
