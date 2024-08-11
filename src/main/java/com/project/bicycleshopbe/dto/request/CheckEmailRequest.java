package com.project.bicycleshopbe.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailRequest {
    @Email(message = "Email không được để trống!")
    @NotBlank(message = "Email Không được để trống!")
    private String email;

    @NotBlank(message = "Số điện thoại không được bỏ trống!")
    @Pattern(regexp = "^(?:\\+84|0)\\d{9}$", message = "Số điện thoại phải bắt đầu bằng +84 hoặc 0 và kết thúc với 9 số!")
    private String phoneNumber;
}
