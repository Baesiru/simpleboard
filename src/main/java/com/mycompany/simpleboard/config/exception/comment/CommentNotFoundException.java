package com.mycompany.simpleboard.config.exception.comment;

import com.mycompany.simpleboard.config.exception.ErrorCode;

public class CommentNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public CommentNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public CommentNotFoundException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public CommentNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public CommentNotFoundException(ErrorCode errorCode, Throwable throwable,
                                    String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
