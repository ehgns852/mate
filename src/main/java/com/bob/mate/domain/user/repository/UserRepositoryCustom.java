package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.user.entity.User;

public interface UserRepositoryCustom {

    User findByEmail(String email);
}
