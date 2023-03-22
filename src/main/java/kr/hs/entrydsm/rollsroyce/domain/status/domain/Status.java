package kr.hs.entrydsm.rollsroyce.domain.status.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_status")
public class Status {

    @Id
    private Long receiptCode;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_code")
    private User user;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Boolean isPrintsArrived;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Boolean isSubmitted;

    private LocalDateTime submittedAt;

    @Column(columnDefinition = "char(5)")
    private String examCode;

    @ColumnDefault("0")
    @Column(nullable = false)
    private boolean isFirstRoundPass;

    public void isSubmitToTrue() {
        this.isSubmitted = true;
    }

    public void cancelIsSubmitted() {
        this.isSubmitted = false;
        if (this.isPrintsArrived) this.isPrintsArrived = false;
    }

    public void updateIsPrintsArrived(boolean isArrived) {
        this.isPrintsArrived = isArrived;
    }

    public Status updateExamCode(String examCode) {
        this.examCode = examCode;
        return this;
    }

    public Status updateIsFirstRoundPass() {
        this.isFirstRoundPass = true;
        return this;
    }
}
