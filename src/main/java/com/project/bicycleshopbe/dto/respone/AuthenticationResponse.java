package com.project.bicycleshopbe.dto.respone;

import com.project.bicycleshopbe.model.permission.AppRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Response DTO for authentication-related operations.
 * Contains various fields to represent authentication status, user details,
 * and related information in a structured format.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private int statusCode;

    private String error;

    private String message;

    private String token;

    private String refreshToken;

    private Long userId;

    private String email;

    private LocalDateTime dateCreate;

    private String fullName;

    private String userCode;

    private Integer gender;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String address;

    private Set<AppRole> roles;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

}