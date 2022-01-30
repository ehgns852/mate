package com.bob.mate.domain.post.domain;

import com.bob.mate.domain.post.dto.PostRequest;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Slf4j
@Table(name = "post")
@ToString(of = {"id", "title", "content"})
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<PostTag> postTags = new ArrayList<>();


    @Builder
    protected Post(String title, String content, List<PostTag> postTags) {
        this.title = title;
        this.content = content;
        this.postTags = postTags;
    }

    /**
     * 연관관계 편의 메서드
     */
    public void addPostTag(PostTag postTag){
        postTags.add(postTag);
        postTag.setPost(this);
    }

    /**
     * 생성 메서드
     */
    public static Post addPost(PostRequest postRequest){
        Post createPost = builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();

        log.info("createPost.postTag = {}", createPost.getPostTags());


        log.info("createPost = {}", createPost);
        log.info("postRequest.getTagName = {}", postRequest.getTagName());

        PostTag postTag = PostTag.addTag(createPost, postRequest.getTagName());

        log.info("Post.getPost = {}", postTag.getPost().getContent());
        log.info("Post.getTag = {}", postTag.getTag().getName());

//        createPost.setPostTag(postTag);

        log.info("After createPost = {}",createPost);

        return createPost;
    }

    public void setPostTag(PostTag postTag) {
        postTags.add(postTag);
        postTag.setPost(this);
    }

}
