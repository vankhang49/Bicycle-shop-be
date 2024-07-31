package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.CartItem;
import com.project.bicycleshopbe.repository.business.ICartItemRepository;
import com.project.bicycleshopbe.service.businnes.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private ICartItemRepository cartItemRepository;

    @Override
    public Page<CartItem> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public Set<CartItem> findAllCartByUserId(Long userId) {
        return cartItemRepository.findAllByAppUserUserId(userId);
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteAllCartByUserId(Long userId) {
        cartItemRepository.deleteAllByAppUserUserId(userId);
    }

    @Override
    public void deleteCartItemByPriceIdAndUserId(Long priceId, Long userId) {
        cartItemRepository.deleteCartItemByPricingPriceIdAndAppUserUserId(priceId, userId);
    }

    @Override
    public void updateQuantityCartItemByPriceIdAndUserId(Long priceId, Long userId, Integer quantity) {
        cartItemRepository.updateQuantityForCartItem(userId, priceId, quantity);
    }
}
