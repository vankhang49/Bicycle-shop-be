package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.dto.request.AuthenticationRequest;
import com.project.bicycleshopbe.dto.request.RegisterRequest;
import com.project.bicycleshopbe.dto.request.UpdatePasswordRequest;
import com.project.bicycleshopbe.dto.respone.AuthenticationResponse;
import com.project.bicycleshopbe.model.permission.AppRole;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.permission.IUserService;
import com.project.bicycleshopbe.service.permission.impl.AuthenticationService;
import com.project.bicycleshopbe.service.permission.impl.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
            Cookie cookie = new Cookie("token", authResponse.getToken());
            cookie.setHttpOnly(true);
//             cookie.setSecure(true); // Chỉ gửi cookie qua HTTPS trong môi trường sản xuất
            cookie.setPath("/");
            cookie.setMaxAge(2 * 60 * 60); // Thời gian tồn tại của cookie (1 ngày)
            response.addCookie(cookie);

            Cookie newRefreshTokenCookie = new Cookie("rft", authResponse.getRefreshToken());
            newRefreshTokenCookie.setHttpOnly(true);
//            cookie.setSecure(true);
            newRefreshTokenCookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(newRefreshTokenCookie);
            System.out.println("call finish");
        }
        return ResponseEntity.status(authResponse.getStatusCode()).body(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request, HttpServletResponse response
    ){
        AuthenticationResponse authResponse = authenticationService.register(request);

        if (authResponse.getStatusCode() == 200) {
            // Thiết lập cookie HTTP-only
            Cookie cookie = new Cookie("token", authResponse.getToken());
            cookie.setHttpOnly(true);
//             cookie.setSecure(true); // Chỉ gửi cookie qua HTTPS trong môi trường sản xuất
            cookie.setPath("/");
            cookie.setMaxAge(2 * 60 * 60); // Thời gian tồn tại của cookie (1 ngày)
            response.addCookie(cookie);

            Cookie newRefreshTokenCookie = new Cookie("rft", authResponse.getRefreshToken());
            newRefreshTokenCookie.setHttpOnly(true);
//            cookie.setSecure(true);
            newRefreshTokenCookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(newRefreshTokenCookie);
            System.out.println("call finish");
        }
        return ResponseEntity.status(authResponse.getStatusCode()).body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletResponse response, @RequestParam(name = "userId") Long userId){
        System.out.println(userId);
        // Thiết lập cookie HTTP-only
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
//         cookie.setSecure(true); // Chỉ gửi cookie qua HTTPS trong môi trường sản xuất
        cookie.setPath("/");
        cookie.setMaxAge(0); // Thời gian tồn tại của cookie (0)
        response.addCookie(cookie);

        Cookie newRefreshTokenCookie = new Cookie("rft", null);
        newRefreshTokenCookie.setHttpOnly(true);
//         cookie.setSecure(true);
        newRefreshTokenCookie.setPath("/");
        newRefreshTokenCookie.setMaxAge(0);
        response.addCookie(newRefreshTokenCookie);

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
    @PutMapping("/update-password")
    public ResponseEntity<?> updateUser(@Validated @RequestBody UpdatePasswordRequest updatePasswordRequest
            , BindingResult bindingResult){
        System.out.println("call update");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        AuthenticationResponse response = authenticationService.updatePassword(updatePasswordRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
