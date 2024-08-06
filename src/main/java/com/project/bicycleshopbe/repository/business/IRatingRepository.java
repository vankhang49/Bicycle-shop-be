package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT rating_id, content, star, date_create, product_id, user_id FROM rating " +
            "WHERE product_id = :productId ORDER BY user_id = :userId DESC, date_create DESC", nativeQuery = true)
    Page<Rating> findAllByProductIdAndSortByUserId(Long productId, Long userId, Pageable pageable);

    Page<Rating> findAllByProductProductId(Long productId, Pageable pageable);
}
