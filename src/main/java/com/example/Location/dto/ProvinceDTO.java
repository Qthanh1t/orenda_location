package com.example.Location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProvinceDTO {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotBlank
    private String name;
}
