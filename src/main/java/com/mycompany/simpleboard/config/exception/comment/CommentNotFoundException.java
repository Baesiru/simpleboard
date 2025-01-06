package com.mycompany.simpleboard.config.exception.comment;

import com.mycompany.simpleboard.config.exception.ErrorCodeImfl;

public class CommentNotFoundException extends RuntimeException {
    private final ErrorCodeImfl errorCodeImfl;
    private final String description;

    public CommentNotFoundException(ErrorCodeImfl errorCodeImfl) {
        super(errorCodeImfl.getDescription());
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorCodeImfl.getDescription();
    }

    public CommentNotFoundException(ErrorCodeImfl errorCodeImfl, String errorDescription) {
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorDescription;
    }

    public CommentNotFoundException(ErrorCodeImfl errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public CommentNotFoundException(ErrorCodeImfl errorCodeIfs, Throwable throwable,
                                    String errorDescription) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorDescription;
    }
}
