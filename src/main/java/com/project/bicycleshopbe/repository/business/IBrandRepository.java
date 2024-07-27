package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> searchAllByCategoryCategoryId(Long categoryId);

    List<Brand> searchAllByCategoryCategoryNameContaining(String categoryName);
}
