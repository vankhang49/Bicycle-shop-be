package com.project.bicycleshopbe.service.dashboard;

import com.project.bicycleshopbe.dto.TotalCustomerDTO;
import com.project.bicycleshopbe.repository.dashboard.ITotalCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalCustomerService {
    @Autowired
    private ITotalCustomerRepository totalCustomerRepository;

    public TotalCustomerDTO getTotalCustomerAndGrowth() {
        return totalCustomerRepository.getTotalCustomerAndGrowth();
    }
}
