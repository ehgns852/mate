package com.bob.mate.domain.post.service;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.dto.OnePostResponse;
import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.entity.Post;
import com.bob.mate.domain.post.entity.PostLike;
import com.bob.mate.domain.post.repository.PostLikeRepository;
import com.bob.mate.domain.post.repository.PostRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.domain.post.dto.LikeResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private final Util util;

    public Page<AllPostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAllPosts(pageable);
    }

    public OnePostResponse getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_POST);

        return postRepository.findPost(postId);
    }

    @Transactional
    public CustomResponse createPost(PostRequest postRequest) {
        User user = util.findCurrentUser();

        Post post = new Post(
                postRequest.getTitle(), postRequest.getContent(), user
        );

        postRepository.save(post);

        return new CustomResponse("글이 생성되었습니다.");
    }

    @Transactional
    public CustomResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = checkPostOwner(postId);

        post.updateTitleAndContent(
                postRequest.getTitle(), postRequest.getContent()
        );

        postRepository.save(post);

        return new CustomResponse("해당 글이 수정되었습니다.");
    }

    @Transactional
    public CustomResponse deletePost(Long postId) {
        Post post = checkPostOwner(postId);

        postRepository.delete(post);

        return new CustomResponse("해당 글이 삭제되었습니다.");
    }

    @Transactional
    public LikeResponse likePost(Long postId) {
        Post post = findPostById(postId);

        User user = util.findCurrentUser();

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());

        if (postLike.isPresent()) {
            post.likePost(post.getLikeCount() - 1);
            postLikeRepository.delete(postLike.get());
            return new LikeResponse(post.getLikeCount(), false);
        } else {
            post.likePost(post.getLikeCount() + 1);
            PostLike newPostLike = new PostLike(user, post);
            postLikeRepository.save(newPostLike);
            return new LikeResponse(post.getLikeCount(), true);
        }
    }

    /**
     * post 객체를 찾는 로직
     * @param postId (post 의 PK)
     * @return postId 로 찾아낸 post 객체
     */
    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
    }

    /**
     * 글 수정이나 삭제시 해당 글에 대한 소유자인지 확인하기 위한 로직
     * @param postId (post 의 PK 값)
     * @return 해당 글의 소유자인게 확인되면 해당글의 객체를 반환함
     */
    private Post checkPostOwner(Long postId) {
        Post post = findPostById(postId);

        User user = util.findCurrentUser();

        if (!post.getUser().equals(user))
            throw new CustomException(ErrorCode.FORBIDDEN_USER);

        return post;
    }
}