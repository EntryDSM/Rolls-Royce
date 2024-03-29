package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo;

import lombok.Getter;

import com.querydsl.core.annotations.QueryProjection;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

@Getter
public class ApplicantVo {

    private final Long receiptCode;

    private final String name;

    private final String telephoneNumber;

    private final Boolean isDaejeon;

    private final String applicationType;

    private final Boolean isPrintsArrived;

    private final Boolean isSubmitted;

    private final Boolean isOutOfHeadcount;

    @QueryProjection
    public ApplicantVo(EntryInfo entryInfo, Status status) {
        this.receiptCode = entryInfo.getReceiptCode();
        this.name = entryInfo.getUserName();
        this.telephoneNumber = entryInfo.getUserTelephoneNumber();
        this.isDaejeon = entryInfo.getIsDaejeon();
        this.applicationType = entryInfo.getApplicationType().name();
        this.isPrintsArrived = status.getIsPrintsArrived();
        this.isSubmitted = status.getIsSubmitted();
        this.isOutOfHeadcount = entryInfo.getIsOutOfHeadcount();
    }
}
