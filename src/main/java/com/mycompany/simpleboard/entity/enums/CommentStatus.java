package com.mycompany.simpleboard.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatus implements Status{
    CREATED("등록"),
    DELETED("삭제"),
    MODIFIED("수정")
    ;

    private final String description;
}
