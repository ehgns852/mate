package com.bob.mate.domain.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<Post> posts;
}
