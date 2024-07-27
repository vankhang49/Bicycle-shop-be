package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {}
