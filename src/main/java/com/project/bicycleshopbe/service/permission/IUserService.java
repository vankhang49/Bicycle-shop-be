package com.project.bicycleshopbe.service.permission;

import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.IGenerateService;

public interface IUserService extends IGenerateService<AppUser> {
    AppUser findByEmail(String email);
    Boolean existsByEmail(String email);
}
