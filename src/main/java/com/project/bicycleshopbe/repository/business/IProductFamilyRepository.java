package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.ProductFamily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductFamilyRepository extends JpaRepository<ProductFamily, Long> {
    List<ProductFamily> searchAllByFamilyNameContainingAndCategoryCategoryNameContaining(String familyName, String categoryName);
}
