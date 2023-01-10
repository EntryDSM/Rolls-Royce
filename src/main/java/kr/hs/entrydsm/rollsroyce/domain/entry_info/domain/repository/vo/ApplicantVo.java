package kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.Getter;

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
