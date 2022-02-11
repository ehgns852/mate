package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;

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
}