package com.example.Location.repository.specfication;

import com.example.Location.entity.ProvinceEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProvinceSpecification {
    public static Specification<ProvinceEntity> searchByCodeOrName(final String term) {
        return (root, query, cb) -> {
            if (term == null || term.trim().isEmpty()) return cb.conjunction();
            String like = "%" + term.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("code")), like)
            );
        };
    }
}
