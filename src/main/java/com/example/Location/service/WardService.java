package com.example.Location.service;

import com.example.Location.dto.WardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WardService {
    WardDTO create(WardDTO dto);
    WardDTO update(Long id, WardDTO dto);
    void delete(Long id);
    List<WardDTO> getByProvinceCode(String provinceCode);
    WardDTO getById(Long id);
    Page<WardDTO> search(String provinceCode, String text, Pageable pageable);
}
