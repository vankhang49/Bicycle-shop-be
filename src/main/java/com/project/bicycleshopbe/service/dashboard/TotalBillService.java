package com.project.bicycleshopbe.service.dashboard;

import com.project.bicycleshopbe.dto.TotalBillDTO;
import com.project.bicycleshopbe.repository.dashboard.ITotalBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalBillService {
    @Autowired
    private ITotalBillRepository totalBillRepository;

    public TotalBillDTO getTotalBillAndGrowth() {
        return totalBillRepository.getTotalBillAndGrowth();
    }
}
