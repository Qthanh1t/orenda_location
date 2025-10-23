package com.example.Location.controller;

import com.example.Location.dto.ProvinceDTO;
import com.example.Location.service.ProvinceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/provinces")
public class ProvinceController {
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<ProvinceDTO> create(@Valid @RequestBody ProvinceDTO provinceDTO) {
        ProvinceDTO province = provinceService.create(provinceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(province);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceDTO> update(@PathVariable Long id, @Valid @RequestBody ProvinceDTO provinceDTO) {
        ProvinceDTO province = provinceService.update(id, provinceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(province);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProvinceDTO> delete(@PathVariable Long id) {
        provinceService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDTO> getById(@PathVariable Long id) {
        ProvinceDTO province = provinceService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(province);
    }

    @GetMapping
    public ResponseEntity<Page<ProvinceDTO>> search(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<ProvinceDTO> provinces = provinceService.search(text, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(provinces);
    }
}
