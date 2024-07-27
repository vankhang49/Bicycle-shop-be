package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.ProductFamily;
import com.project.bicycleshopbe.service.IGenerateService;

import java.util.List;

public interface IProductFamilyService extends IGenerateService<ProductFamily> {
    List<ProductFamily> searchAllByFamilyNameAndCategoryName(String familyName, String categoryName);
}
