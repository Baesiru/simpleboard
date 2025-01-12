package com.mycompany.simpleboard.config.exception.likes;

import com.mycompany.simpleboard.config.exception.ErrorCode;

public class LikesNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public LikesNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public LikesNotFoundException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public LikesNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public LikesNotFoundException(ErrorCode errorCode, Throwable throwable,
                                  String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
