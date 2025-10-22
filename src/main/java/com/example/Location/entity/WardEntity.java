package com.example.Location.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wards")
public class WardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=50)
    private String code;

    @Column(nullable=false)
    private String name;

    @Column(name = "province_code", nullable=false, length=50)
    private String provinceCode;
}
