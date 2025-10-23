package com.example.Location.controller;

import com.example.Location.dto.WardDTO;
import com.example.Location.service.WardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wards")
public class WardController {
    private final WardService wardService;

    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @PostMapping
    public ResponseEntity<WardDTO> create(@Valid @RequestBody WardDTO dto) {
        WardDTO created = wardService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WardDTO> update(@Valid @RequestBody WardDTO dto, @PathVariable Long id) {
        WardDTO updated = wardService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WardDTO> delete(@PathVariable Long id) {
        wardService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getByProvinceCode/{provinceCode}")
    public ResponseEntity<List<WardDTO>> getByProvinceCode(@PathVariable String provinceCode) {
        List<WardDTO> wards = wardService.getByProvinceCode(provinceCode);
        return ResponseEntity.status(HttpStatus.OK).body(wards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardDTO> getById(@PathVariable Long id) {
        WardDTO dto = wardService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<WardDTO>> search(
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<WardDTO> wards = wardService.search(provinceCode, text, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(wards);
    }

}
