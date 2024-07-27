package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.Brand;
import com.project.bicycleshopbe.service.IGenerateService;

import java.util.List;

public interface IBrandService extends IGenerateService<Brand> {
    List<Brand> findAllByCategoryId(Long categoryId);

    List<Brand> findAllByCategoryName(String categoryName);
}
