package com.bob.mate.domain.post.mapper;

import com.bob.mate.domain.post.domain.Tag;
import com.bob.mate.domain.post.dto.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "tagName", source = "name")
    TagResponse mapEntityToResponse(Tag tag);
}
