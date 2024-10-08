package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.dto.request.AppUserRequest;
import com.project.bicycleshopbe.dto.respone.AuthenticationResponse;
import com.project.bicycleshopbe.dto.respone.ErrorDetail;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.permission.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(404).body("Không tìm thấy khách hàng!");
        } else {
            return ResponseEntity.ok(customers);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees(@RequestParam(name = "userCode", defaultValue = "") String userCode,
                                             @RequestParam(name = "fullName", defaultValue = "") String fullName,
                                             @RequestParam(name = "page", defaultValue = "0") int page

    ) {
        if (page < 0) {
            page = 0;
        }
        Page<AppUser> employees = userService.searchAllEmployeeByUserCodeOrFullName(userCode, fullName, PageRequest.of(page, 10));
        if (employees.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy nhân viên!");
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        AppUser user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng!");
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Creates a new user with the provided details.
     *
     * @param appUserRequest The request containing user details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the status of the creation.
     */
    @PostMapping()
    public ResponseEntity<?> createUser(@Validated @RequestBody AppUserRequest appUserRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse response = userService.saveUser(appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates an existing user with the provided details.
     *
     * @param id             The ID of the user to be updated.
     * @param appUserRequest The request containing updated user details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the status of the update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated @RequestBody AppUserRequest appUserRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse response = userService.updateUser(id, appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        AppUser appUser = userService.findById(id);
        if (appUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return ResponseEntity.status(200).body("Xoá tài khoản thành công!");
    }


    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        AppUser appUser = userService.findById(id);
        if (appUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.disableUser(id);
        return ResponseEntity.status(200).body("Khóa tài khoản thành công!");
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
        AppUser appUser = userService.findById(id);
        if (appUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.enableUser(id);
        return ResponseEntity.status(200).body("Khôi phục tài khoản thành công!");
    }
}
