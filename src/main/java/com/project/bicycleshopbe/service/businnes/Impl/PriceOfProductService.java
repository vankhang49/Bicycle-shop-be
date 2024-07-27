package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.Pricing;
import com.project.bicycleshopbe.repository.business.IPricingRepository;
import com.project.bicycleshopbe.service.businnes.IPriceOfProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceOfProductService implements IPriceOfProductService {
    @Autowired
    private IPricingRepository pricingRepository;

    @Override
    public Page<Pricing> findAll(Pageable pageable) {
        return pricingRepository.findAll(pageable);
    }

    @Override
    public List<Pricing> findAll() {
        return pricingRepository.findAll();
    }

    @Override
    public List<Pricing> searchAllByProductId(Long productId) {
        return pricingRepository.searchAllByProductProductId(productId);
    }

    @Override
    public Pricing findById(Long id) {
        return pricingRepository.findById(id).orElse(null);
    }

    @Override
    public Pricing searchByProductIdAndImgColor(Long productId, String imgColor) {
        return pricingRepository.findByProductProductIdAndImgColorContaining(productId, imgColor);
    }

    @Override
    public void save(Pricing pricing) {
        pricingRepository.save(pricing);
    }

    @Override
    public void remove(Long id) {
        pricingRepository.deleteById(id);
    }
}
