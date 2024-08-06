package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.Rating;
import com.project.bicycleshopbe.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatingService extends IGenerateService<Rating> {
    Page<Rating> findAllRatingsByProductIdAndOrderByUser(Long productId ,Long userId, Pageable pageable);
    Page<Rating> findAllRatingsByProductId(Long productId, Pageable pageable);
}
