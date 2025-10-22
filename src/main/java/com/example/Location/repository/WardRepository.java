package com.example.Location.repository;

import com.example.Location.entity.WardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<WardEntity, Long>, JpaSpecificationExecutor<WardEntity> {
    boolean existsByCode(String code);
    List<WardEntity> findByProvinceCode(String provinceCode);
    Optional<WardEntity> findByCode(String code);
    long countByProvinceCode(String provinceCode);
}
