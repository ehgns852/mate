package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.user.entity.QUser;
import com.bob.mate.domain.user.entity.QUserProfile;
import com.bob.mate.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.bob.mate.domain.user.entity.QUser.*;
import static com.bob.mate.domain.user.entity.QUserProfile.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    /**
     * UserEmail 조회
     * User + UserProfile fetch join 한방 쿼리
     */
    @Override
    public User findByEmail(String email) {
        return queryFactory
                .selectFrom(user)
                .innerJoin(user.userProfile, userProfile)
                .fetchJoin()
                .where(user.email.eq(email))
                .fetchOne();
    }

    @Override
    public User findByOauthId(String providerId) {
      return queryFactory
              .selectFrom(user)
              .innerJoin(user.userProfile, userProfile)
              .fetchJoin()
              .where(userProfile.providerId.eq(providerId))
              .fetchOne();
    }

}
