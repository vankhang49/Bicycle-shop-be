package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPricingRepository extends JpaRepository<Pricing, Long> {
    List<Pricing> searchAllByProductProductId(Long productId);
    Pricing findByProductProductIdAndImgColorContaining(Long productId, String imgColor);
}
