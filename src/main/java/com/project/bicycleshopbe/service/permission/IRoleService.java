package com.project.bicycleshopbe.service.permission;

import com.project.bicycleshopbe.model.permission.AppRole;
import com.project.bicycleshopbe.service.IGenerateService;

public interface IRoleService extends IGenerateService<AppRole> {
    AppRole findRoleByRoleName(String roleName);
}
