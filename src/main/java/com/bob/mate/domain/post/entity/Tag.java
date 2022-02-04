package com.bob.mate.domain.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

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

    @Column(name = "tag_name")
    private String name;

    @ManyToOne(fetch = LAZY,cascade =  ALL)
    @JoinColumn(name = "post_id")
    private Post post;


    public Tag(String name) {
        this.name = name;
    }


    /**
     * 생성 메서드
     */
    public static List<Tag> addTag(List<String> name) {

        return name.stream().map(Tag::new).collect(Collectors.toList());

    }

    public void setPost(Post post) {
        this.post = post;
    }
}
