package com.project.bicycleshopbe.service.permission;

import com.project.bicycleshopbe.dto.request.AppUserRequest;
import com.project.bicycleshopbe.dto.respone.AuthenticationResponse;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGenerateService<AppUser> {
    AppUser findByEmail(String email);
    Boolean existsByEmail(String email);

    Page<AppUser> searchAllCustomerByUserCodeOrFullName(String userCode, String fullName, Pageable pageable);

    Page<AppUser> searchAllEmployeeByUserCodeOrFullName(String userCode, String fullName, Pageable pageable);

    /**
     * Disable an entity by its ID.
     *
     * @param id the ID of the entity to disable
     */
    void disableUser(Long id);

    /**
     * Enable an entity by its ID.
     *
     * @param id the ID of the entity to enable
     */
    void enableUser(Long id);

    /**
     * Saves a new user based on the provided {@link AppUserRequest}.
     *
     * @param appUserRequest the request object containing user details to be saved
     * @return an {@link AuthenticationResponse} indicating the outcome of the save operation
     */
    AuthenticationResponse saveUser(AppUserRequest appUserRequest);

    /**
     * Update user based on the provided {@link AppUserRequest}.
     *
     * @param userId the id to find user details to be updated
     * @param appUserRequest the request object containing user details to be updated
     * @return an {@link AuthenticationResponse} indicating the outcome of the update operation
     */
    AuthenticationResponse updateUser(Long userId, AppUserRequest appUserRequest);
}
