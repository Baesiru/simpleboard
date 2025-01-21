package com.mycompany.simpleboard.config.exception.user;

import com.mycompany.simpleboard.config.exception.ErrorCode;

public class MailErrorException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public MailErrorException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public MailErrorException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public MailErrorException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public MailErrorException(ErrorCode errorCode, Throwable throwable,
                              String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
