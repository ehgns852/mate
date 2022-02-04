package com.bob.mate.domain.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.bob.mate.domain.post.dto.QPostQueryDto is a Querydsl Projection type for PostQueryDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostQueryDto extends ConstructorExpression<PostQueryDto> {

    private static final long serialVersionUID = 932616205L;

    public QPostQueryDto(com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> tag) {
        super(PostQueryDto.class, new Class<?>[]{String.class, String.class, String.class}, title, content, tag);
    }

}

