package kr.hs.entrydsm.rollsroyce.domain.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.BaseTimeEntity;
import kr.hs.entrydsm.rollsroyce.domain.post.domain.type.PostType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String content;

    @Column(columnDefinition = "enum(9)", nullable = false)
    private PostType type;

    @Column(columnDefinition = "BIT(1) default 0")
    private Boolean isPinned;

    @Column(columnDefinition = "varchar(255)")
    private String image;
}
