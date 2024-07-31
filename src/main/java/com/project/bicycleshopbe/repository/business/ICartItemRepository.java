package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findAllByAppUserUserId(Long userId);

    void deleteAllByAppUserUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_items WHERE user_id = :userId AND price_id = :priceId", nativeQuery = true)
    void deleteCartItemByPricingPriceIdAndAppUserUserId(Long priceId, Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart_items set quantity = :quantity WHERE user_id = :userId AND price_id = :priceId", nativeQuery = true)
    void updateQuantityForCartItem(@Param( "userId") Long userId, @Param( "priceId") Long priceId, @Param( "quantity") Integer quantity);

}
