package com.project.bicycleshopbe.dto.request;

import com.project.bicycleshopbe.model.permission.AppRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a request to update an existing user in the system.
 * This includes various user details such as username, password, contact information, and role.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private Long userId;

    private String email;

    private String password;

    private String userCode;

    private String avatar;

    private String backgroundImage;

    private String fullName;

    private LocalDate dateOfBirth;

    private Integer gender;

    private String phoneNumber;

    private String address;

    private List<AppRole> role;

    private LocalDate dateCreate;
}
