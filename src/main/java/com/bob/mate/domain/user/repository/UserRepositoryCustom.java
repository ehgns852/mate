package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    User findByEmail(String email);

    User findByOauthId(String providerId);

    Optional<User> findUserAllProfileById(Long id);
}
