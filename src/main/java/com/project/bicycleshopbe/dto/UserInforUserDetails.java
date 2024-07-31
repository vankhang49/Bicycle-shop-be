package com.project.bicycleshopbe.dto;

import com.project.bicycleshopbe.model.permission.AppRole;
import com.project.bicycleshopbe.model.permission.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserInforUserDetails implements UserDetails {

    private AppUser user;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserInforUserDetails(AppUser user, Set<AppRole> roles) {
        this.user = user;
        if (roles != null) {
            for (AppRole role : roles) {
                //ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE, ROLE_CUSTOMER
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(authority);
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}