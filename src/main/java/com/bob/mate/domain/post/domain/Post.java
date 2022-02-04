package com.bob.mate.domain.post.domain;

import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.user.entity.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

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
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    protected Post(String title, String content,Tag tags) {
        this.title = title;
        this.content = content;
        this.tags.add(tags);
    }


    /**
     * 생성 메서드
     */
    public static Post addPost(PostRequest postRequest){


        List<Tag> tags = Tag.addTag(postRequest.getTagName());

        Post createPost = builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        for (Tag tag : tags) {
            createPost.addTag(tag);
        }
        return createPost;
    }

    /**
     * 연관관계 메서드
     */
    public void addTag(Tag tag){
        this.tags.add(tag);
        tag.setPost(this);
    }



}
