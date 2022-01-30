package com.bob.mate.domain.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Slf4j
@ToString(of = {"id","post","tag"})
@NoArgsConstructor(access = PROTECTED)
public class PostTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    /**
     * 생성 메서드
     */
    public static PostTag addTag(Post post, String tagName) {
        Tag tag = Tag.addTag(tagName);
        PostTag postTag = new PostTag(post,tag);

        log.info("postTag = {}", postTag);

        return postTag;
    }

    /**
     * 연관관계 편의 메서드
     */
    public void addPost(Post post, Tag tag){
        post.setPostTag(this);
        tag.setPostTag(this);
    }


    public void setPost(Post post) {
        this.post = post;
    }
}
