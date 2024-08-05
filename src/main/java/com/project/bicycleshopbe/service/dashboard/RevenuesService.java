package com.project.bicycleshopbe.service.dashboard;

import com.project.bicycleshopbe.dto.RevenueDTO;
import com.project.bicycleshopbe.repository.dashboard.IRevenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenuesService {
    @Autowired
    private IRevenuesRepository revenueRepository;

    public RevenueDTO getRevenueData(int option) {
        return revenueRepository.getTotalRevenueAndGrowth(option);
    }

}
