package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> searchAllByProductProductId(Long productId);
}
