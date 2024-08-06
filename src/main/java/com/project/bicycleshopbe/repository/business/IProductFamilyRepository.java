package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.ProductFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductFamilyRepository extends JpaRepository<ProductFamily, Long> {
    List<ProductFamily> searchAllByFamilyNameContainingAndCategoryCategoryNameContaining(String familyName, String categoryName);
}
