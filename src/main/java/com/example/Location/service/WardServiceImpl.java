package com.example.Location.service;

import com.example.Location.dto.WardDTO;
import com.example.Location.entity.WardEntity;
import com.example.Location.exception.BadRequestException;
import com.example.Location.exception.NotFoundException;
import com.example.Location.repository.ProvinceRepository;
import com.example.Location.repository.WardRepository;
import com.example.Location.repository.specfication.WardSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WardServiceImpl implements WardService {
    private final WardRepository wardRepository;
    private final ProvinceRepository provinceRepository;
    private final ModelMapper modelMapper;

    public WardServiceImpl(WardRepository wardRepository, ProvinceRepository provinceRepository, ModelMapper modelMapper) {
        this.wardRepository = wardRepository;
        this.provinceRepository = provinceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public WardDTO create(WardDTO wardDTO){
        if (wardRepository.existsByCode(wardDTO.getCode())) {
            throw new BadRequestException("Mã xã/phường đã tồn tại");
        }
        WardEntity wardEntity = modelMapper.map(wardDTO, WardEntity.class);
        WardEntity saved = wardRepository.save(wardEntity);
        return modelMapper.map(saved, WardDTO.class);
    }

    @Override
    @Transactional
    public WardDTO update(Long id, WardDTO dto){
        WardEntity wardEntity = wardRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy mã Xã/Phường"));
        if(!wardEntity.getCode().equals(dto.getCode()) && wardRepository.existsByCode(dto.getCode())){
            throw new BadRequestException("Mã Xã/Phường đã tồn tại");
        }
        if(!provinceRepository.existsByCode(dto.getProvinceCode())){
            throw new BadRequestException("Mã tỉnh không tồn tại");
        }
        wardEntity.setCode(dto.getCode());
        wardEntity.setName(dto.getName());
        wardEntity.setProvinceCode(dto.getProvinceCode());
        WardEntity saved = wardRepository.save(wardEntity);
        return modelMapper.map(saved, WardDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id){
        WardEntity wardEntity = wardRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy Xã/Phường"));
        wardRepository.delete(wardEntity);
    }

    @Override
    public List<WardDTO> getByProvinceCode(String provinceCode){
        if(!provinceRepository.existsByCode(provinceCode)){
            throw new NotFoundException("Mã tỉnh không tồn tại");
        }
        List<WardEntity> wardEntities = wardRepository.findByProvinceCode(provinceCode);
        return wardEntities.stream().map(wardEntity -> modelMapper.map(wardEntity, WardDTO.class)).toList();
    }

    @Override
    public WardDTO getById(Long id){
        WardEntity wardEntity = wardRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy Xã/Phường"));
        return modelMapper.map(wardEntity, WardDTO.class);
    }

    @Override
    public Page<WardDTO> search(String provinceCode, String text, Pageable pageable){
        Specification<WardEntity> spec = (root, query, cb) -> cb.conjunction();
        Specification<WardEntity> specificationByProvinceCode = WardSpecification.searchByProvinceCode(provinceCode);
        Specification<WardEntity> specificationByNameOrCode = WardSpecification.searchByNameOrCode(text);
        spec = spec.and(specificationByNameOrCode).and(specificationByProvinceCode);
        Page<WardEntity> wardEntitiesPage = wardRepository.findAll(spec, pageable);
        return wardEntitiesPage.map(wardEntity -> modelMapper.map(wardEntity, WardDTO.class));
    }
}
