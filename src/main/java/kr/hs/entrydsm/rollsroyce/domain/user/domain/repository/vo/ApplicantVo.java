package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicantVo {

    private final Long receiptCode;

    private final String name;

    private final String email;

    private final Boolean isDaejeon;

    private final ApplicationType applicationType;

    private final Boolean isPrintsArrived;

    private final Boolean isSubmitted;

    private final Boolean isOutOfHeadcount;

}
