package com.project.bicycleshopbe.controller;

import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.model.business.BillItem;
import com.project.bicycleshopbe.service.businnes.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/shopping-cart")
@CrossOrigin("*")
public class ShoppingCartRestController {

    @Autowired
    private IBillService billService;

    @PostMapping("/pay")
    public ResponseEntity<?> saveBill(@RequestBody Bill bill) {
        System.out.println(bill);

        // Thiết lập mối quan hệ hai chiều
        for (BillItem item : bill.getBillItems()) {
            item.setBill(bill);
        }

        billService.save(bill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
