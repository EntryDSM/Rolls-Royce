package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo;

import lombok.Getter;

import com.querydsl.core.annotations.QueryProjection;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

@Getter
public class ApplicantVo {

    private final Long receiptCode;

    private final String name;

    private final String email;

    private final Boolean isDaejeon;

    private final String applicationType;

    private final Boolean isPrintsArrived;

    private final Boolean isSubmitted;

    private final Boolean isOutOfHeadcount;

    @QueryProjection
    public ApplicantVo(User user, Status status) {
        this.receiptCode = user.getReceiptCode();
        this.name = user.getName();
        this.email = user.getEmail();
        this.isDaejeon = user.getIsDaejeon();
        this.applicationType = user.getApplicationType().name();
        this.isPrintsArrived = status.getIsPrintsArrived();
        this.isSubmitted = status.getIsSubmitted();
        this.isOutOfHeadcount = user.getIsOutOfHeadcount();
    }
}
