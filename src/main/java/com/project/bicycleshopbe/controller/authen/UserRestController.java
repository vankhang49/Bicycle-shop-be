package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.permission.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomers(@RequestParam(name = "userCode", defaultValue = "") String userCode,
                                         @RequestParam(name = "fullName", defaultValue = "") String fullName,
                                         @RequestParam(name = "page", defaultValue = "0") int page

    ) {
        if (page < 0) {
            page = 0;
        }

        Page<AppUser> customers = userService.searchAllCustomerByUserCodeOrFullName(userCode, fullName, PageRequest.of(page, 10));
        if (customers.isEmpty()) {
            return ResponseEntity.status(400).body("Không tìm thấy khách hàng!");
        } else {
            return ResponseEntity.ok(customers);
        }
    }
}
