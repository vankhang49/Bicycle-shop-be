package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.ProductImage;
import com.project.bicycleshopbe.repository.business.IProductImageRepository;
import com.project.bicycleshopbe.service.businnes.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private IProductImageRepository productImageRepository;

    @Override
    public Page<ProductImage> findAll(Pageable pageable) {
        return productImageRepository.findAll(pageable);
    }

    @Override
    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public List<ProductImage> searchAllByProductId(Long productId) {
        return productImageRepository.searchAllByProductProductId(productId);
    }

    @Override
    public ProductImage findById(Long id) {
        return productImageRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductImage productImage) {
        productImageRepository.save(productImage);
    }

    @Override
    public void remove(Long id) {
        productImageRepository.deleteById(id);
    }
}
