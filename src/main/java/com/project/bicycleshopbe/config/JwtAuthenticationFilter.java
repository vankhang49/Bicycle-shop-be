package com.project.bicycleshopbe.config;

import com.project.bicycleshopbe.model.permission.RefreshToken;
import com.project.bicycleshopbe.service.permission.impl.JwtService;
import com.project.bicycleshopbe.service.permission.impl.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

/**
 * JWT authentication filter to validate JWT tokens from incoming requests.
 * This filter intercepts requests, extracts and validates JWT tokens,
 * and sets up Spring Security context if the token is valid.
 * <p>
 * Author: KhangDV
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String rft = null;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
                if ("rft".equals(cookie.getName())) {
                    rft = cookie.getValue();
                }
            }
        }

        if (rft == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(rft);
            if (refreshToken.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            RefreshToken refresh = refreshToken.get();
            email = refresh.getUser().getEmail();

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwt)) {
                    setAuthentication(request, userDetails);
                } else {
                    if (refreshTokenService.checkAndDeleteExpiredToken(refreshToken.get())) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    } else {
                        // Tạo mới access token
                        System.out.println("remake");
                        String newAccessToken = jwtService.generateToken(userDetails);
                        refresh.setExpiryDate(Instant.now().plusMillis(86400000));
                        refreshTokenService.updateRefreshToken(refresh);

                        Cookie newAccessTokenCookie = new Cookie("token", newAccessToken);
                        newAccessTokenCookie.setHttpOnly(true);
                        newAccessTokenCookie.setPath("/");
                        newAccessTokenCookie.setMaxAge(2 * 60 * 60);
                        response.addCookie(newAccessTokenCookie);

                        Cookie newRefreshTokenCookie = new Cookie("rft", refresh.getToken());
                        newRefreshTokenCookie.setHttpOnly(true);
                        newRefreshTokenCookie.setPath("/");
                        newRefreshTokenCookie.setMaxAge(24 * 60 * 60);
                        response.addCookie(newRefreshTokenCookie);

                        setAuthentication(request, userDetails);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
