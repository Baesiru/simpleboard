package com.mycompany.simpleboard.config.exception.user;

import com.mycompany.simpleboard.config.exception.ErrorCode;

public class SamePasswordException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public SamePasswordException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public SamePasswordException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public SamePasswordException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public SamePasswordException(ErrorCode errorCode, Throwable throwable,
                                   String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
