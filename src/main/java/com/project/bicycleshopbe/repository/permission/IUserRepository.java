package com.project.bicycleshopbe.repository.permission;

import com.project.bicycleshopbe.model.permission.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    Boolean existsByEmail(String email);
}
