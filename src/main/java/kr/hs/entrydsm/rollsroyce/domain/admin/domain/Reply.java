package kr.hs.entrydsm.rollsroyce.domain.admin.domain;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
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
import javax.persistence.OneToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(150)", nullable = false)
    private String title;

    @Column(columnDefinition = "varchar(5000)", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id", nullable = false)
    private Qna qna;

    @Builder
    public Reply(String title, String content, Admin admin, Qna qna) {
        this.title = title;
        this.content = content;
        this.admin = admin;
        this.qna = qna;
    }
}
