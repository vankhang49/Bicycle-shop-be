package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface IBillService extends IGenerateService<Bill> {
    Page<Bill> searchAllByBillCodeAndFullName(String userCode, String fullName, Pageable pageable);

    Slice<Bill> searchAllByUserId(Long userId, Pageable pageable);

    void updateReceivedBill(Long billId);
}
