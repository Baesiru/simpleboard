package com.mycompany.simpleboard.dto.user.register;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,50}$",
            message = "한글, 영어 또는 숫자로 2자 이상 50자 이하로 입력해주세요."
    )
    private String username;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?!.*[\\\\{}()<>$%^&*_=|`]).{8,100}$",
            message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다."
    )
    private String password;
    @Pattern(
            regexp = "^[0-9a-zA-Z]{1,50}@[0-9a-zA-Z]{1,24}+(\\.[0-9a-zA-Z]+){1,24}$",
            message = "올바른 이메일 형식을 입력하세요 (예: example@domain.com)"
    )
    private String email;
}
