package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    public ApplicantVo(Long receiptCode, String name, String email, Boolean isDaejeon, ApplicationType applicationType,
                       Boolean isPrintsArrived, Boolean isSubmitted, Boolean isOutOfHeadcount) {
        this.receiptCode = receiptCode;
        this.name = name;
        this.email = email;
        this.isDaejeon = isDaejeon;
        this.applicationType = applicationType.name();
        this.isPrintsArrived = isPrintsArrived;
        this.isSubmitted = isSubmitted;
        this.isOutOfHeadcount = isOutOfHeadcount;
    }

}
