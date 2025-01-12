package com.mycompany.simpleboard.dto.user.management;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailAndCodeRequest {
    @NotBlank(message = "올바른 이메일 형식을 입력하세요 (예: example@domain.com)")
    @Email(
            message = "올바른 이메일 형식을 입력하세요 (예: example@domain.com)"
    )
    private String email;
    private String code;
}
