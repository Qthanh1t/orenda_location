package com.example.Location.service;

import com.example.Location.dto.ProvinceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProvinceService {
    ProvinceDTO create(ProvinceDTO dto);
    ProvinceDTO update(Long id, ProvinceDTO dto);
    void delete(Long id);
    ProvinceDTO getById(Long id);
    Page<ProvinceDTO> search(String text, Pageable pageable);
}
