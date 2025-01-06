package com.mycompany.simpleboard.config.exception.user;

import com.mycompany.simpleboard.config.exception.ErrorCodeImfl;

public class ExistsUsernameException extends RuntimeException {
    private final ErrorCodeImfl errorCodeImfl;
    private final String description;

    public ExistsUsernameException(ErrorCodeImfl errorCodeImfl) {
        super(errorCodeImfl.getDescription());
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorCodeImfl.getDescription();
    }

    public ExistsUsernameException(ErrorCodeImfl errorCodeImfl, String errorDescription) {
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorDescription;
    }

    public ExistsUsernameException(ErrorCodeImfl errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ExistsUsernameException(ErrorCodeImfl errorCodeIfs, Throwable throwable,
                                   String errorDescription) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorDescription;
    }
}
