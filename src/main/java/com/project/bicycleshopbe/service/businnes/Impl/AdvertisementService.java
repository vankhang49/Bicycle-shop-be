package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.Advertisement;
import com.project.bicycleshopbe.repository.business.IAdvertisementRepository;
import com.project.bicycleshopbe.service.businnes.IAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    private IAdvertisementRepository advertisementRepository;

    @Override
    public Page<Advertisement> findAll(Pageable pageable) {
        return advertisementRepository.findAll(pageable);
    }

    @Override
    public List<Advertisement> findAll() {
        return advertisementRepository.findAll();
    }

    @Override
    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Advertisement advertisement) {
        advertisementRepository.save(advertisement);
    }

    @Override
    public void remove(Long id) {
        advertisementRepository.deleteById(id);
    }
}
