package com.mycompany.simpleboard.dto.user.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "한글, 영어 또는 숫자로 2자 이상 50자 이하로 입력해주세요.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,50}$",
            message = "한글, 영어 또는 숫자로 2자 이상 50자 이하로 입력해주세요."
    )
    private String username;

    @NotBlank(message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$\n",
            message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다."
    )
    private String password;
}
