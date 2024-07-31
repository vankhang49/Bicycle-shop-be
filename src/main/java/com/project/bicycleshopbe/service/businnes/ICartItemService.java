package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.CartItem;
import com.project.bicycleshopbe.service.IGenerateService;

import java.util.Set;

public interface ICartItemService extends IGenerateService<CartItem> {
    Set<CartItem> findAllCartByUserId(Long userId);

    void deleteAllCartByUserId(Long userId);

    void deleteCartItemByPriceIdAndUserId(Long priceId, Long userId);

    void updateQuantityCartItemByPriceIdAndUserId(Long priceId, Long userId, Integer quantity);
}
