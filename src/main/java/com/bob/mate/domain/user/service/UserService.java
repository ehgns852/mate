package com.bob.mate.domain.user.service;

import com.bob.mate.domain.post.dto.MyCommentResponse;
import com.bob.mate.domain.post.dto.MyPostResponse;
import com.bob.mate.domain.user.dto.UserProfileQueryDto;
import com.bob.mate.domain.user.dto.UserProfileRequest;
import com.bob.mate.domain.user.dto.UserProfileResponse;
import com.bob.mate.domain.user.dto.UserResponse;
import com.bob.mate.domain.user.entity.UploadFile;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.repository.UserRepository;
import com.bob.mate.global.config.redis.RedisUtil;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.SecurityUtil;
import com.bob.mate.global.util.Util;
import com.bob.mate.global.util.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final Util util;

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    /**
     * 토큰으로 받아온 사용자 정보 넘겨주는 메서드
     */
    public User findCurrentUserId() {
        return util.findCurrentUser();
    }

    /**
     * 헤더에서 엑세스 토큰을 받아와서 회원 정보 및 회원 프로필이 수정 되어있는지 확인하고 반환하는 메서드
     */
    public UserResponse findValidateProfileSaveUser() {
        User findUser = findCurrentUserId();
        boolean validateProfileSaveUser = validateProfileSaveUser(findUser.getId());
        return UserResponse.builder()
                .id(findUser.getId())
                .email(findUser.getEmail())
                .role(findUser.getRole())
                .address(findUser.getUserProfile().getAddress())
                .imageUrl("https://d3afymv2nzz1pw.cloudfront.net/doji.png")
                .gender(findUser.getUserProfile().getGender())
                .phoneNumber(findUser.getUserProfile().getPhoneNumber())
                .age(findUser.getUserProfile().getAge())
                .profileSaveUser(validateProfileSaveUser)
                .build();
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
    public CustomResponse deleteUser() {

        User findUser = findCurrentUserId();

        userRepository.delete(findUser);

        redisUtil.deleteData(String.valueOf(findUser.getId()));

        return new CustomResponse("회원 탈퇴가 완료 되었습니다.");

    }

    /**
     * 회원 프로필 생성 및 변경
     */
    @Transactional
    public UserProfileResponse updateProfile(MultipartFile multipartFile, UserProfileRequest userProfileRequest) throws IOException {

        User findUser = findCurrentUserId();

        UploadFile uploadFile = fileStore.storeFile(multipartFile);

        if (uploadFile != null) {
            findUser.addUploadImg(
                    userProfileRequest.getNickName(),
                    userProfileRequest.getAddress(),
                    userProfileRequest.getPhoneNumber(),
                    userProfileRequest.getEmail(),
                    userProfileRequest.getGender(),
                    uploadFile);

            return new UserProfileResponse("https://d3afymv2nzz1pw.cloudfront.net/doji.png", "회원 프로필이 저장 되었습니다.");

        } else {
            findUser.updateUserProfile(
                    userProfileRequest.getNickName(),
                    userProfileRequest.getAddress(),
                    userProfileRequest.getPhoneNumber(),
                    userProfileRequest.getEmail(),
                    userProfileRequest.getGender());

            return new UserProfileResponse("https://d3afymv2nzz1pw.cloudfront.net/doji.png", "회원 프로필이 저장 되었습니다.");
        }
    }

    /**
     * 현재 유저가 작성한 모든 글 반환
     * @param pageable 페이지 정보 담는 변수
     * @return 현재 유저가 작성한 모든 글 반환
     */
    public Page<MyPostResponse> getAllMyPosts(Pageable pageable) {
        return userRepository.findAllMyPosts(pageable, findCurrentUserId());
    }

    /**
     * 현재 유저가 작성한 모든 댓글 반환
     * @param pageable 페이지 정보 담는 변수
     * @return 현재 유저가 작성한 모든 댓글 반환
     */
    public Page<MyCommentResponse> getAllMyComments(Pageable pageable) {
        return userRepository.findAllMyComments(pageable, findCurrentUserId());
    }


    public UserProfileQueryDto findUserProfileById() {
        User findUser = findCurrentUserId();
        return userRepository.findUserProfileById(findUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }



    /**
     * 중복 로직 findById
     */
    private User getFindById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }

    /**
     * 회원 프로필이 저장 되어있는지 확인하는 메서드
     */
    private boolean validateProfileSaveUser(Long id) {

        UserProfileQueryDto findUser = userRepository.findUserProfileById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        if (findUser.getImgUrl() == null || findUser.getNickName() == null || findUser.getGender() == null || findUser.getAddress() == null ||
                findUser.getEmail() == null || findUser.getPhoneNumber() == null) {
            return false;
        }
        return true;
    }

}

