package kr.hs.entrydsm.rollsroyce.domain.qna.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.BaseTimeEntity;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_qna")
public class Qna extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "varchar(5000)", nullable = false)
    private String content;

    @Column(columnDefinition = "BIT(1) default 0")
    private Boolean isPublic;

    @Column(columnDefinition = "BIT(1) default 0")
    private Boolean isReplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Builder
    public Qna(String title, String content, Boolean isPublic, User user) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.isReplied = false;
        this.user = user;
    }

    public void updateFeed(String title, String content, Boolean isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
    }

    public void updateIsReplied(Boolean isReplied) {
        this.isReplied = isReplied;
    }

    public String getName() {
        return user.getName();
}
