package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.dto.request.*;
import com.project.bicycleshopbe.dto.respone.AuthenticationResponse;
import com.project.bicycleshopbe.dto.respone.ErrorDetail;
import com.project.bicycleshopbe.model.permission.AppRole;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.permission.IUserService;
import com.project.bicycleshopbe.service.permission.impl.AuthenticationService;
import com.project.bicycleshopbe.service.permission.impl.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response
    ){
        AuthenticationResponse authResponse = authenticationService.authentication(request);

        if (authResponse.getStatusCode() == 200) {
            // Thiết lập cookie HTTP-only
            ResponseCookie cookie = ResponseCookie.from("token", authResponse.getToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(2 * 60 * 60)
                    .build(); // Thời gian tồn tại của cookie (0)

            ResponseCookie newRefreshTokenCookie = ResponseCookie.from("rft", authResponse.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
            response.addHeader("Set-Cookie", newRefreshTokenCookie.toString());
        }
        return ResponseEntity.status(authResponse.getStatusCode()).body(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Validated @RequestBody RegisterRequest request, BindingResult bindingResult,
            HttpServletResponse response

    ){
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error);
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        AuthenticationResponse authResponse = authenticationService.register(request);

        if (authResponse.getStatusCode() == 200) {
            // Thiết lập cookie HTTP-only

            ResponseCookie cookie = ResponseCookie.from("token", authResponse.getToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(2 * 60 * 60)
                    .build(); // Thời gian tồn tại của cookie (0)

            ResponseCookie newRefreshTokenCookie = ResponseCookie.from("rft", authResponse.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
            response.addHeader("Set-Cookie", newRefreshTokenCookie.toString());
            System.out.println("call finish");
        }
        return ResponseEntity.status(authResponse.getStatusCode()).body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletResponse response, @RequestParam(name = "userId") Long userId){
        System.out.println(userId);
        // Thiết lập cookie HTTP-only
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build(); // Thời gian tồn tại của cookie (0)

        ResponseCookie newRefreshTokenCookie = ResponseCookie.from("rft", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        response.addHeader("Set-Cookie", newRefreshTokenCookie.toString());

        refreshTokenService.removeRefreshTokenByUserId(userId);

        return ResponseEntity.status(200).body("Đăng xuất thành công!");
    }

    @GetMapping("/user-role")
    public ResponseEntity<?> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByEmail(username);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        Set<AppRole> roles = user.getRoles();
        return ResponseEntity.status(200).body(roles);
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     * @throws RuntimeException if an error occurs while retrieving user information.
     */
    @GetMapping("/get-profile")
    public ResponseEntity<?> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates the information of a user.
     *
     * @param updatePasswordRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @PutMapping("/update-password/{userId}")
    public ResponseEntity<?> updatePasswordUser(@Validated @RequestBody UpdatePasswordRequest updatePasswordRequest
            , BindingResult bindingResult, @PathVariable Long userId){
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse response = authenticationService.updatePassword(userId ,updatePasswordRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-info")
    public ResponseEntity<?> updateInformation(@Validated @RequestBody AppUserRequest appUserRequest
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        AuthenticationResponse response = authenticationService.updateInfo(appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates avatar and background image of a user.
     *
     * @param updateAvatarRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    @PatchMapping("/update-image")
    public ResponseEntity<?> updateAvatarUser(@RequestBody UpdateAvatarRequest updateAvatarRequest) {
        AuthenticationResponse response = authenticationService.updateAvatarImage(updateAvatarRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
