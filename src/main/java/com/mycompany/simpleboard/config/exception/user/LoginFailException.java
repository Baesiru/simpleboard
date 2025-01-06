package com.mycompany.simpleboard.config.exception.user;

import com.mycompany.simpleboard.config.exception.ErrorCodeImfl;

public class LoginFailException extends RuntimeException {
    private final ErrorCodeImfl errorCodeImfl;
    private final String description;

    public LoginFailException(ErrorCodeImfl errorCodeImfl) {
        super(errorCodeImfl.getDescription());
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorCodeImfl.getDescription();
    }

    public LoginFailException(ErrorCodeImfl errorCodeImfl, String errorDescription) {
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorDescription;
    }

    public LoginFailException(ErrorCodeImfl errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public LoginFailException(ErrorCodeImfl errorCodeIfs, Throwable throwable,
                                   String errorDescription) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorDescription;
    }
}
