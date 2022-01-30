package com.bob.mate.domain.post.mapper;

import com.bob.mate.domain.post.domain.Post;
import com.bob.mate.domain.post.domain.Tag;
import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.dto.PostResponse;
import com.bob.mate.domain.post.repository.TagRepository;
import com.bob.mate.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//@RequiredArgsConstructor
//@Mapper(componentModel = "spring")
//public abstract class PostMapper {
//
//    private final TagRepository tagRepository;
//
//    @Mapping(target = "tag", expression = "java(getTagName(post))")
//    public abstract PostResponse mapEntityToResponse(Post post);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "tag", expression = "java(findTagByName(postRequest))")
//    public abstract Post mapRequestToEntity(PostRequest postRequest);
//
//    public abstract void updateEntityFromDto(PostRequest postRequest, @MappingTarget Post post);
//
//    Tag findTagByName(PostRequest postRequest) {
//        return tagRepository.findByName(postRequest.getTagName())
//                .orElseThrow(() -> new BadRequestException("해당 태그를 찾을 수 없습니다."));
//    }
//
//    String getTagName(Post post) {
//        return post.getTag().getName();
//    }
//}
