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
@Table(name = TableConstant.CAFE_OWNER_TABLE)
public class CafeOwner {
    @Id
    private String id;

    @Column(name = "owner_name", length = 100, nullable = false)
    private String name;

    @Column(name = "number_phone", unique = true, length = 15)
    private String numberPhone;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;
}
