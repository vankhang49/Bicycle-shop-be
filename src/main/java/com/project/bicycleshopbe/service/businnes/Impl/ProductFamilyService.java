package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.ProductFamily;
import com.project.bicycleshopbe.repository.business.IProductFamilyRepository;
import com.project.bicycleshopbe.service.businnes.IProductFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFamilyService implements IProductFamilyService {
    @Autowired
    private IProductFamilyRepository productFamilyRepository;

    @Override
    public List<ProductFamily> searchAllByFamilyNameAndCategoryName(String familyName, String categoryName) {
        return productFamilyRepository.searchAllByFamilyNameContainingAndCategoryCategoryNameContaining(familyName,categoryName);
    }

    @Override
    public Page<ProductFamily> findAll(Pageable pageable) {
        return productFamilyRepository.findAll(pageable);
    }

    @Override
    public List<ProductFamily> findAll() {
        return productFamilyRepository.findAll();
    }

    @Override
    public ProductFamily findById(Long id) {
        return productFamilyRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductFamily productType) {
        productFamilyRepository.save(productType);
    }

    @Override
    public void remove(Long id) {
        productFamilyRepository.deleteById(id);
    }
}
