package com.mycompany.simpleboard.config.exception.board;

import com.mycompany.simpleboard.config.exception.ErrorCodeImfl;

public class BoardNotFoundException extends RuntimeException {
    private final ErrorCodeImfl errorCodeImfl;
    private final String description;

    public BoardNotFoundException(ErrorCodeImfl errorCodeImfl) {
        super(errorCodeImfl.getDescription());
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorCodeImfl.getDescription();
    }

    public BoardNotFoundException(ErrorCodeImfl errorCodeImfl, String errorDescription) {
        this.errorCodeImfl = errorCodeImfl;
        this.description = errorDescription;
    }

    public BoardNotFoundException(ErrorCodeImfl errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public BoardNotFoundException(ErrorCodeImfl errorCodeIfs, Throwable throwable,
                                String errorDescription) {
        super(throwable);
        this.errorCodeImfl = errorCodeIfs;
        this.description = errorDescription;
    }
}
