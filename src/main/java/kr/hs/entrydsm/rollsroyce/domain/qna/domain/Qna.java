package kr.hs.entrydsm.rollsroyce.domain.qna.domain;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_qna")
public class Qna {
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
    private User user;

    @Builder
    public Qna(String title, String content, Boolean isPublic, Boolean isReplied, User user) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.isReplied = isReplied;
        this.user = user;
    }

    public void updateFeed(String title, String content, Boolean isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
    }
}
