package com.mycompany.simpleboard.dto.user.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$\n",
            message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다."
    )
    private String currPassword;

    @NotBlank(message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$\n",
            message = "대문자, 소문자, 특수문자를 포함하고 8자 이상이어야 합니다."
    )
    private String changedPassword;
}
