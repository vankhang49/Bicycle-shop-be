package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPricingRepository extends JpaRepository<Pricing, Long> {
    List<Pricing> searchAllByProductProductId(Long productId);
    Pricing findByProductProductIdAndImgColorContaining(Long productId, String imgColor);
}
