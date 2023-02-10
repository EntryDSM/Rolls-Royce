package kr.hs.entrydsm.rollsroyce.domain.qna.domain;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}