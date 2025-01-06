package com.mycompany.simpleboard.config.exception.user;

import com.mycompany.simpleboard.config.exception.ErrorCodeImfl;

public class ExistsEmailException extends RuntimeException {
    private final ErrorCodeImfl errorCodeImfl;
    private final String description;

    public ExistsEmailException(ErrorCodeImfl errorCodeImfl) {
        super(errorCodeImfl.getDescription());
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorCodeImfl.getDescription();
    }

    public ExistsEmailException(ErrorCodeImfl errorCodeImfl, String errorDescription) {
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorDescription;
    }

    public ExistsEmailException(ErrorCodeImfl errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ExistsEmailException(ErrorCodeImfl errorCodeIfs, Throwable throwable,
                                String errorDescription) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorDescription;
    }
}
