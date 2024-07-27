package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.BillItem;
import com.project.bicycleshopbe.repository.business.IBillItemsRepository;
import com.project.bicycleshopbe.service.businnes.IBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BillItemService implements IBillItemService {
    @Autowired
    private IBillItemsRepository billItemsRepository;

    @Override
    public List<BillItem> findAll() {
        return billItemsRepository.findAll();
    }
    @Override
    public Page<BillItem> findAll(Pageable pageable) {
        return billItemsRepository.findAll(pageable);
    }

    @Override
    public BillItem findById(Long id) {
        return billItemsRepository.findById(id).orElse(null);
    }

    @Override
    public void save(BillItem billItem) {
        billItemsRepository.save(billItem);
    }

    @Override
    public void remove(Long id) {
        billItemsRepository.deleteById(id);
    }
}
