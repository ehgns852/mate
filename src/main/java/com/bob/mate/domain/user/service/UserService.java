package com.bob.mate.domain.user.service;

import com.bob.mate.domain.user.dto.UserProfileQueryDto;
import com.bob.mate.domain.user.dto.UserProfileRequest;
import com.bob.mate.domain.user.dto.UserProfileResponse;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.repository.UserRepository;
import com.bob.mate.global.config.redis.RedisUtil;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.file.FileStore;
import com.bob.mate.domain.user.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final FileStore fileStore;

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }


    /**
     * 회원 프로필 단건 조회
     */
    public User findById(Long id){
        return getFindById(id);
    }


    /**
     * 회원 탈퇴
     */
    @Transactional
    public CustomResponse deleteUser(Long userId) {

        User findUser = getFindById(userId);

        userRepository.delete(findUser);

        redisUtil.deleteData(String.valueOf(userId));

        return new CustomResponse("회원 탈퇴가 완료 되었습니다.");

    }

    /**
     * 회원 프로필 생성 및 변경
     */
    @Transactional
    public UserProfileResponse updateProfile(MultipartFile multipartFile, UserProfileRequest userProfileRequest) throws IOException {

        User findUser = userRepository.findUserAllProfileById(userProfileRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        UploadFile uploadFile = fileStore.storeFile(multipartFile);

        if (uploadFile != null) {
            findUser.addUploadImg(
                    userProfileRequest.getNickName(),
                    userProfileRequest.getAddress(),
                    userProfileRequest.getPhoneNumber(),
                    userProfileRequest.getEmail(),
                    userProfileRequest.getGender(),
                    uploadFile);

            return new UserProfileResponse("회원 프로필이 저장 되었습니다.", uploadFile.getStoreFilename());

        } else {
            findUser.updateUserProfile(
                    userProfileRequest.getNickName(),
                    userProfileRequest.getAddress(),
                    userProfileRequest.getPhoneNumber(),
                    userProfileRequest.getEmail(),
                    userProfileRequest.getGender());

            return new UserProfileResponse("회원 프로필이 저장 되었습니다.", findUser.getUserProfile().getUploadFile().getStoreFilename());
        }
    }

    public UserProfileQueryDto findUserProfileById(Long id) {
        return userRepository.findUserProfileById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }



    /**
     * 중복 로직 findById
     */
    private User getFindById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }

}

