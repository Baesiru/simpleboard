package com.mycompany.simpleboard.config.exception.user;

import com.mycompany.simpleboard.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    EXISTS_USER_EMAIL(403, "이미 존재하는 이메일입니다."),
    EXISTS_USER_NAME(403, "이미 존재하는 아이디입니다."),
    LOGIN_FAIL(401, "로그인 정보가 일치하지 않습니다."),
    USER_UNREGISTER_FAIL(500, "회원 탈퇴에 실패했습니다."),
    USER_NOT_MATCHED(401, "작성자와 아이디가 일치하지 않습니다."),
    SAME_PASSWORD_ERROR(400, "현재 비밀번호와 같은 비밀번호는 사용할 수 없습니다."),
    CODE_NOT_EQUALS(400, "인증번호가 일치하지 않습니다."),
    MAIL_SEND_ERROR(501, "메일 전송 중 문제가 발생하였습니다.")
    ;

    private final Integer httpCode;
    private final String description;
}
