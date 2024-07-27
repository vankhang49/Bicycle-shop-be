package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.Pricing;
import com.project.bicycleshopbe.service.IGenerateService;

import java.util.List;

public interface IPriceOfProductService extends IGenerateService<Pricing> {
    List<Pricing> searchAllByProductId(Long productId);
    Pricing searchByProductIdAndImgColor(Long productId, String imgColor);
}
