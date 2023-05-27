package kr.hs.entrydsm.rollsroyce.domain.faq.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.BaseTimeEntity;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.type.FaqType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_faq")
public class Faq extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 11)
    private FaqType faqType;

    @OneToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Builder
    public Faq(String title, String content, FaqType faqType, Admin admin) {
        this.title = title;
        this.content = content;
        this.faqType = faqType;
        this.admin = admin;
    }

    public void update(String title, String content, FaqType faqType, Admin admin) {
        this.title = title;
        this.content = content;
        this.faqType = faqType;
        this.admin = admin;
    }
}
