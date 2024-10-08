package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.repository.business.IBillRepository;
import com.project.bicycleshopbe.service.businnes.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepository billRepository;
    @Override
    public Page<Bill> findAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public Slice<Bill> searchAllByUserId(Long userId, Pageable pageable) {
        return billRepository.searchByAppUserUserIdOrderByPaidAscDateCreateDesc(userId, pageable);
    }

    @Override
    public Page<Bill> searchAllByBillCodeAndFullName(String billCode, String fullName, Pageable pageable) {
        return billRepository.searchAllByBillCodeContainingAndAppUserFullNameContaining(billCode, fullName, pageable);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void remove(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public void updateReceivedBill(Long billId) {
        billRepository.updateReceivedBill(billId);
    }
}
