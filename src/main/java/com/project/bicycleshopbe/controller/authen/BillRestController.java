package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.service.businnes.Impl.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/bills")
public class BillRestController {

    @Autowired
    private BillService billService;

    @GetMapping
    public ResponseEntity<?> getAllBills(@RequestParam(name = "billCode", defaultValue = "")String billCode,
                                         @RequestParam(name = "fullName", defaultValue = "") String fullName,
                                         @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        Page<Bill> bills = billService.searchAllByBillCodeAndFullName(billCode, fullName, PageRequest.of(page, 10));
        if (bills.isEmpty()) {
            return ResponseEntity.status(404).body("Không có đơn hàng nào được tìm thấy!");
        }
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllBillsByUserId(@PathVariable("userId") Long userId) {
        List<Bill> bills = billService.searchAllByUserId(userId);
        if (bills.isEmpty()) {
            return ResponseEntity.status(404).body("Không có đơn hàng nào được tìm thấy!");
        }
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> getBillById(@PathVariable("billId") Long billId) {
        Bill bill = billService.findById(billId);
        if (bill == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bill);
    }

    @PatchMapping("/receivedBill/{billId}")
    public ResponseEntity<?> updateReceivedBill(@PathVariable("billId") Long billId) {
        Bill bill = billService.findById(billId);
        if (bill == null) {
            return ResponseEntity.noContent().build();
        }
        billService.updateReceivedBill(billId);
        return ResponseEntity.status(200).body("Đã xác nhận!");
    }
}
