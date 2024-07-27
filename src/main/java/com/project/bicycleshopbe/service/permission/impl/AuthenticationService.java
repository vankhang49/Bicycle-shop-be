package com.project.bicycleshopbe.service.permission.impl;

import com.project.bicycleshopbe.dto.UserInforUserDetails;
import com.project.bicycleshopbe.dto.request.AuthenticationRequest;
import com.project.bicycleshopbe.dto.request.UpdatePasswordRequest;
import com.project.bicycleshopbe.dto.respone.AuthenticationResponse;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.repository.permission.IRoleRepository;
import com.project.bicycleshopbe.repository.permission.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service implementation for managing user authentication and registration.
 * This class provides methods for user registration, authentication, and token management.
 * <p>
 * Author: KhangDV
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param request The authentication request containing login credentials.
     * @return An {@link AuthenticationResponse} containing the JWT token and authentication status.
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail());
            System.out.println(user);
            UserInforUserDetails userDetails = new UserInforUserDetails(user, user.getRoles());
            var jwtToken = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .token(jwtToken)
                    .fullName(user.getFullName())
                    .message("Đăng nhập thành công!!!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .message("Đăng nhập thất bại")
                    .statusCode(500).build();
        }
    }

    /**
     * Retrieves the information of the authenticated user.
     *
     * @param email The username of the authenticated user.
     * @return An {@link AuthenticationResponse} containing user information.
     */
    public AuthenticationResponse getMyInfo(String email) {
        AppUser user = userRepository.findByEmail(email);
        if (user != null) {
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .message("Thành công!")
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .userCode(user.getUserCode())
                    .dateCreate(user.getDateCreate())
                    .dateOfBirth(user.getDateOfBirth())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .roles(user.getRoles())
                    .fullName(user.getFullName())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .build();
        } else {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();
        }
    }

    /**
     * Updates the password of the authenticated user.
     *
     * @param updatePasswordRequest The user request containing the updated password details.
     * @return An {@link AuthenticationResponse} indicating the status of the password update operation.
     */
    public AuthenticationResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        String email = updatePasswordRequest.getEmail();
        AppUser user = userRepository.findByEmail(email);
        if (user == null) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();
        }
        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Vui lòng nhập đúng mật khẩu!").build();
        }
        if (updatePasswordRequest.getNewPassword() == null && updatePasswordRequest.getNewPassword().isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Mật khẩu mới không được để trống")
                    .build();

        }
        if (!updatePasswordRequest.getConfirmPassword().equals(updatePasswordRequest.getNewPassword())) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Mật khẩu không trùng khớp!").build();
        }
        try {
            user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(401)
                    .message("Cập nhật mật khẩu thất bại")
                    .error(e.getMessage())
                    .build();
        }
        UserInforUserDetails userDetails = new UserInforUserDetails(user, user.getRoles());
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật mật khẩu thành công!")
                .userId(user.getUserId())
                .email(user.getEmail())
                .userCode(user.getUserCode())
                .dateCreate(user.getDateCreate())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .address(user.getAddress())
                .token(jwtToken)
                .build();
    }
}
