package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.Brand;
import com.project.bicycleshopbe.repository.business.IBrandRepository;
import com.project.bicycleshopbe.service.businnes.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {
    @Autowired
    private IBrandRepository brandRepository;

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public List<Brand> findAllByCategoryId(Long categoryId) {
        return brandRepository.searchAllByCategoryCategoryId(categoryId);
    }

    @Override
    public List<Brand> findAllByCategoryName(String categoryName) {
        return brandRepository.searchAllByCategoryCategoryNameContaining(categoryName);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void remove(Long id) {
        brandRepository.deleteById(id);
    }
}
