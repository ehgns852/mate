package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.post.dto.MyCommentResponse;
import com.bob.mate.domain.post.dto.MyPostResponse;
import com.bob.mate.domain.user.dto.UserProfileQueryDto;
import com.bob.mate.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepositoryCustom {

    User findByOauthId(String providerId);

    Optional<User> findUserAllProfileById(Long id);

    Optional<UserProfileQueryDto> findUserProfileById(Long id);

    Page<MyPostResponse> findAllMyPosts(Pageable pageable, User user);

    Page<MyCommentResponse> findAllMyComments(Pageable pageable, User user);
}
