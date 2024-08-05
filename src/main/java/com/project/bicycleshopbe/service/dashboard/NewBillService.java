package com.project.bicycleshopbe.service.dashboard;

import com.project.bicycleshopbe.dto.NewBillDTO;
import com.project.bicycleshopbe.repository.dashboard.NewBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewBillService {
    @Autowired
    private NewBillRepository newBillRepository;

    public List<NewBillDTO> getAllNewBills() {
        return newBillRepository.getNewBillDTOList();
    }
}
