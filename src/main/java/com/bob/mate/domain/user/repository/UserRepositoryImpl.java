package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.user.dto.QUserProfileQueryDto;
import com.bob.mate.domain.user.dto.UserProfileQueryDto;
import com.bob.mate.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.bob.mate.domain.user.entity.QUploadFile.uploadFile;
import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;



    @Override
    public User findByOauthId(String providerId) {
      return queryFactory
              .selectFrom(user)
              .innerJoin(user.userProfile, userProfile)
              .fetchJoin()
              .innerJoin(userProfile.uploadFile, uploadFile)
              .fetchJoin()
              .where(userProfile.providerId.eq(providerId))
              .fetchOne();
    }

    @Override
    public Optional<User> findUserAllProfileById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .innerJoin(user.userProfile, userProfile)
                .fetchJoin()
                .innerJoin(userProfile.uploadFile, uploadFile)
                .fetchJoin()
                .where(user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<UserProfileQueryDto> findUserProfileById(Long id) {
        return Optional.ofNullable(queryFactory
                .select(new QUserProfileQueryDto(
                        uploadFile.storeFilename,
                        userProfile.nickName,
                        userProfile.gender,
                        userProfile.address,
                        user.email,
                        userProfile.phoneNumber))
                .from(user)
                .innerJoin(user.userProfile, userProfile)
                .innerJoin(userProfile.uploadFile, uploadFile)
                .where(user.id.eq(id))
                .fetchOne());
    }

}
