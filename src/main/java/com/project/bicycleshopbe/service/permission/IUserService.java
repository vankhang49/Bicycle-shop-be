package com.project.bicycleshopbe.service.permission;

import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGenerateService<AppUser> {
    AppUser findByEmail(String email);
    Boolean existsByEmail(String email);

    Page<AppUser> searchAllCustomerByUserCodeOrFullName(String userCode, String fullName, Pageable pageable);
}
