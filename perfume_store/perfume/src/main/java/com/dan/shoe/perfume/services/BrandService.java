package com.dan.shoe.perfume.services;

import com.dan.shoe.perfume.dtos.responses.ResponseMessage;
import com.dan.shoe.perfume.models.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    boolean existsByName(String name);
    Brand findByName(String name);
    ResponseMessage create(Brand brand);
    ResponseMessage update(Brand brand, Long id);
    ResponseMessage delete(Long id);
    Brand getById(Long id);
    Page<Brand> getAll(String name, Pageable pageable);
}
