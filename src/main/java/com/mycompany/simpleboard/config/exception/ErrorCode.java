package com.mycompany.simpleboard.config.exception;

public interface ErrorCode {
    Integer getHttpCode();
    Integer getErrorCode();
    String getDescription();
}
