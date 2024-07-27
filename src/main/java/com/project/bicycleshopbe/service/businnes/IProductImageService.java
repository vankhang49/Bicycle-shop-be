package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.ProductImage;
import com.project.bicycleshopbe.service.IGenerateService;

import java.util.List;

public interface IProductImageService extends IGenerateService<ProductImage> {
    List<ProductImage> searchAllByProductId(Long productId);
}
