package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.Rating;
import com.project.bicycleshopbe.repository.business.IRatingRepository;
import com.project.bicycleshopbe.service.businnes.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private IRatingRepository ratingRepository;

    @Override
    public Page<Rating> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public Page<Rating> findAllRatingsByProductIdAndOrderByUser(Long productId, Long userId, Pageable pageable) {
        return ratingRepository.findAllByProductIdAndSortByUserId(productId, userId, pageable);
    }

    @Override
    public Page<Rating> findAllRatingsByProductId(Long productId, Pageable pageable) {
        return ratingRepository.findAllByProductProductId(productId, pageable);
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void remove(Long id) {
        ratingRepository.deleteById(id);
    }
}
