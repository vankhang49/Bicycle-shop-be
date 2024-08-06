package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPromotionRepository extends JpaRepository<Promotion, Long> {
}
