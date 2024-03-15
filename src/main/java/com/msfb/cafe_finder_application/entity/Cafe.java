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
@Table(name = TableConstant.CAFE_TABLE)
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "cafe_name", nullable = false)
    private String cafeName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "url_location", nullable = false)
    private String urlLocation;

}
