package com.example.Location.repository;

import com.example.Location.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long>, JpaSpecificationExecutor<ProvinceEntity> {
    boolean existsByCode(String code);
    Optional<ProvinceEntity> findByCode(String code);
}
