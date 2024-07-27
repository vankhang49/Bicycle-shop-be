package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> searchAllByProductNameContaining(String name, Pageable pageable);

    Page<Product> searchAllByProductFamilyCategoryCategoryNameContaining(String categoryName, Pageable pageable);

    Page<Product> searchAllByProductNameContainingAndProductFamilyFamilyNameContainingAndProductFamilyCategoryCategoryNameContaining(String productName, String familyName, String categoryName, Pageable pageable);

    Page<Product> searchAllByProductNameContainingAndProductFamilyFamilyNameContainingAndProductFamilyCategoryCategoryNameContainingAndBrandBrandNameContaining(String productName, String familyName, String categoryName, String brandName, Pageable pageable);
}
