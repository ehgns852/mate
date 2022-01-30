package com.bob.mate.domain.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(of = {"id","name"})
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }



    /**
     * 생성 메서드
     */
    public static Tag addTag(String name) {
        return new Tag(name);
    }

    public void setPostTag(PostTag postTag) {
        this.postTags = postTags;
    }
}
