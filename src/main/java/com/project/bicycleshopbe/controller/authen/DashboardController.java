package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.dto.*;
import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.service.businnes.Impl.BillService;
import com.project.bicycleshopbe.service.dashboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing dashboard data.
 * This class provides endpoints for retrieving various statistics and data for the dashboard.
 * <p>
 * Author: KhangDV
 */
@RestController
@RequestMapping("/api/auth/dashboard")
public class DashboardController {
    @Autowired
    private TotalCustomerService totalCustomerService;

    @Autowired
    private TotalBillService totalBillService;

    @Autowired
    private RevenuesService revenuesService;

    @Autowired
    private NewBillService newBillService;

    @Autowired
    private BillService billService;

    /**
     * Retrieves total customer count and growth data.
     *
     * @return A {@link ResponseEntity} containing the {@link TotalCustomerDTO}.
     */
//    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/total-customer")
    public ResponseEntity<TotalCustomerDTO> getTotalCustomer() {
        return ResponseEntity.ok(totalCustomerService.getTotalCustomerAndGrowth());
    }

    /**
     * Retrieves total bill count and growth data.
     *
     * @return A {@link ResponseEntity} containing the {@link TotalBillDTO}.
     */
//    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/total-bill")
    public ResponseEntity<TotalBillDTO> getTotalBill() {
        return ResponseEntity.ok(totalBillService.getTotalBillAndGrowth());
    }

    /**
     * Retrieves revenue data based on the specified option.
     *
     * @param option The option indicating the time period for revenue data.
     * @return A {@link ResponseEntity} containing the {@link RevenueDTO}.
     */
//    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/revenues/{option}")
    public ResponseEntity<RevenueDTO> getRevenues(@PathVariable(name = "option") int option) {
        return ResponseEntity.ok(revenuesService.getRevenueData(option));
    }

    /**
     * Retrieves the list of new bills.
     *
     * @return A {@link ResponseEntity} containing a list of {@link Bill}.
     */
//    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @GetMapping("/new-bills")
    public ResponseEntity<?> getAllBills(@RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "dateCreate"));
        Page<Bill> bills = billService.searchAllByBillCodeAndFullName("", "", pageRequest);
        if (bills.isEmpty()) {
            return ResponseEntity.status(404).body("Không có đơn hàng nào được tìm thấy!");
        }
        return ResponseEntity.ok(bills);
    }
}
