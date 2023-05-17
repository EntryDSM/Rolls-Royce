package kr.hs.entrydsm.rollsroyce.domain.status.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_status")
public class Status {

    @Id
    private Long receiptCode;

    @MapsId
    @OneToOne
    @JoinColumn(name = "receipt_code")
    private EntryInfo entryInfo;

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

<<<<<<< main
<<<<<<< main
    @ColumnDefault("0")
    @Column(nullable = false)
    private boolean isSecondRoundPass;
=======
=======
    @ColumnDefault("0")
    @Column(nullable = false)
    private boolean isSecondRoundPass;

>>>>>>> ♻️ :: spotlessApply
    @Builder
    public Status(
            Long receiptCode,
            EntryInfo entryInfo,
            Boolean isPrintsArrived,
            Boolean isSubmitted,
            LocalDateTime submittedAt,
            String examCode,
            Boolean isFirstRoundPass) {
        this.receiptCode = receiptCode;
        this.entryInfo = entryInfo;
        this.isPrintsArrived = isPrintsArrived;
        this.isSubmitted = isSubmitted;
        this.submittedAt = submittedAt;
        this.examCode = examCode;
        this.isFirstRoundPass = isFirstRoundPass;
    }
>>>>>>> ♻️ :: Entity 통일

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
