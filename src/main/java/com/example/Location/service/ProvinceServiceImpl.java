package com.example.Location.service;

import com.example.Location.dto.ProvinceDTO;
import com.example.Location.entity.ProvinceEntity;
import com.example.Location.exception.BadRequestException;
import com.example.Location.exception.NotFoundException;
import com.example.Location.repository.ProvinceRepository;
import com.example.Location.repository.WardRepository;
import com.example.Location.repository.specfication.ProvinceSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, WardRepository wardRepository, ModelMapper modelMapper) {
        this.provinceRepository = provinceRepository;
        this.wardRepository = wardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ProvinceDTO create(ProvinceDTO provinceDTO) {
        if (provinceRepository.existsByCode(provinceDTO.getCode())) {
            throw new BadRequestException("Mã tỉnh đã tồn tại");
        }
        ProvinceEntity provinceEntity = modelMapper.map(provinceDTO, ProvinceEntity.class);
        ProvinceEntity savedProvinceEntity = provinceRepository.save(provinceEntity);
        return modelMapper.map(savedProvinceEntity, ProvinceDTO.class);
    }

    @Override
    @Transactional
    public ProvinceDTO update(Long id, ProvinceDTO provinceDTO) {
        ProvinceEntity provinceEntity = provinceRepository.findById(id).orElseThrow(()-> new NotFoundException("Không tìm thấy tỉnh"));
        if (!provinceEntity.getCode().equals(provinceDTO.getCode()) && provinceRepository.existsByCode(provinceDTO.getCode())) {
            throw new BadRequestException("Mã tỉnh đã tồn tại");
        }
        provinceEntity.setCode(provinceDTO.getCode());
        provinceEntity.setName(provinceDTO.getName());
        ProvinceEntity savedProvinceEntity = provinceRepository.save(provinceEntity);
        return modelMapper.map(savedProvinceEntity, ProvinceDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProvinceEntity provinceEntity = provinceRepository.findById(id).orElseThrow(()-> new NotFoundException("Không tìm thấy tỉnh"));
        long wards = wardRepository.countByProvinceCode(provinceEntity.getCode());
        if (wards > 0) {
            throw new BadRequestException("Không thể xóa tỉnh vì đã có xã/phường sử dụng mã tỉnh");
        }
        provinceRepository.delete(provinceEntity);
    }

    @Override
    public ProvinceDTO getById(Long id) {
        ProvinceEntity provinceEntity = provinceRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy tỉnh"));
        return modelMapper.map(provinceEntity, ProvinceDTO.class);
    }

    @Override
    public Page<ProvinceDTO> search(String term,  Pageable pageable) {
        Specification<ProvinceEntity> specification = ProvinceSpecification.searchByCodeOrName(term);
        Page<ProvinceEntity> provinceEntityPage = provinceRepository.findAll(specification, pageable);
        return provinceEntityPage.map(provinceEntity -> modelMapper.map(provinceEntity, ProvinceDTO.class));
    }
}
