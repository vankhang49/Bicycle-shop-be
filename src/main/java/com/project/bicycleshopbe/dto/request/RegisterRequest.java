package com.project.bicycleshopbe.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Validator {
    @Email(message = "Email không được để trống!")
    @NotBlank(message = "Email Không được để trống!")
    private String newEmail;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 8, max = 50, message = "Mật khẩu phải từ 8 đến 50 chữ!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&/_])[A-Z][A-Za-z\\d@$!%*/?&]{7,49}$",
            message = "Mật khẩu phải bắt đầu bằng một chữ hoa, chứa ít nhất một chữ thường, một chữ số, ký tự đặc biệt (@$!%*?&/_), và phải dài từ 8 đến 50 ký tự!")
    private String newPassword;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống!")
    private String confirmPassword;

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdatePasswordRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UpdatePasswordRequest updatePasswordRequest = (UpdatePasswordRequest) target;
        String newPasswordCheck = updatePasswordRequest.getNewPassword();
        String confirmPasswordCheck = updatePasswordRequest.getConfirmPassword();
        if (!confirmPasswordCheck.equals(newPasswordCheck)){
            errors.rejectValue("confirmPassword","", "Mật khẩu không trùng khớp!!");
        }
    }

}
