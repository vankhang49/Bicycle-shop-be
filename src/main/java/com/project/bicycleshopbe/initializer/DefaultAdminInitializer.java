package com.project.bicycleshopbe.initializer;


import com.project.bicycleshopbe.model.permission.AppRole;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.permission.IRoleService;
import com.project.bicycleshopbe.service.permission.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class DefaultAdminInitializer implements CommandLineRunner {

    private final IUserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;

    public DefaultAdminInitializer(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Danh sách các vai trò mặc định
        String[] roleNames = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_CUSTOMER"};

        // Tạo các vai trò nếu chúng chưa tồn tại
        for (String roleName : roleNames) {
            AppRole role = roleService.findRoleByRoleName(roleName);
            if (role == null) {
                role = new AppRole();
                role.setRoleName(roleName);
                roleService.save(role);
            }
        }

        // Kiểm tra xem đã có admin trong cơ sở dữ liệu chưa
        if (!userService.existsByEmail("admin@gmail.com")) {
            //Tìm kiếm trong DB có Role tên là ROLE_ADMIN không?
            AppRole adminRole = roleService.findRoleByRoleName("ROLE_ADMIN");
            if (adminRole == null) {
                // Nếu chưa có, tạo mới Role ROLE_ADMIN
                adminRole = new AppRole();
                adminRole.setRoleName("ROLE_ADMIN");
                roleService.save(adminRole);
            }
            // Tạo tài khoản admin mặc định
            AppUser admin = new AppUser();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            Set<AppRole> roles = new HashSet<>();
            AppRole role = roleService.findRoleByRoleName("ROLE_ADMIN");
            roles.add(role);
            admin.setRoles(roles);

            userService.save(admin);
        }
    }
}
