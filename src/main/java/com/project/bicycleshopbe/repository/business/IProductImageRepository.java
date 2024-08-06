package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> searchAllByProductProductId(Long productId);
}
