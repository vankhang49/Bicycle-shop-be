package com.project.bicycleshopbe.repository.permission;

import com.project.bicycleshopbe.model.permission.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.roles r " +
            "WHERE (u.userCode LIKE %:userCode% or u.fullName LIKE %:fullName%) AND r.roleId = 4")
    Page<AppUser> searchAllByUserCodeOrFullNameAndRoleId(@Param("userCode") String userCode, @Param("fullName") String fullName, Pageable pageable);

}
