package com.example.Location.repository.specfication;

import com.example.Location.entity.WardEntity;
import org.springframework.data.jpa.domain.Specification;

public class WardSpecification {
    public static Specification<WardEntity> searchByNameOrCode(String term) {
        return (root, query, cb) -> {
            if (term == null || term.trim().isEmpty()) return cb.conjunction();
            String like = "%" + term.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("code")), like)
            );
        };
    }
    public static Specification<WardEntity> searchByProvinceCode(String provinceCode) {
        return (root, query, cb) -> {
            if (provinceCode == null || provinceCode.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("provinceCode"), provinceCode);
        };
    }

}
