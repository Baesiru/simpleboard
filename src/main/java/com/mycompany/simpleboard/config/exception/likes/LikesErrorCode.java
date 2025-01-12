package com.mycompany.simpleboard.config.exception.likes;

import com.mycompany.simpleboard.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikesErrorCode implements ErrorCode {
    LIKE_NOT_FOUND(404, "해당 추천을 찾을 수 없습니다."),
    LIKE_ALREADY_FOUND(409, "이미 추천을 누른 게시글입니다.");

    private final Integer httpCode;
    private final String description;
}
