package com.project.bicycleshopbe.service.permission.impl;

import com.project.bicycleshopbe.model.permission.AppRole;
import com.project.bicycleshopbe.repository.permission.IRoleRepository;
import com.project.bicycleshopbe.service.permission.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Page<AppRole> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<AppRole> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public AppRole findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void save(AppRole appRole) {
        roleRepository.save(appRole);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public AppRole findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
