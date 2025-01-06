package com.mycompany.simpleboard.config.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Auth {
    // @Auth 애너테이션 추가 시 해당 경로 엔드포인트는 인터셉터로 토큰 검사
}