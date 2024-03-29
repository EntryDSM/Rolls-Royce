package kr.hs.entrydsm.rollsroyce.domain.entryinfo.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;

@Getter
@Builder
public class StatusResponse {
    private final Long receiptCode;
    private final String name;
    private final String phoneNumber;
    private final boolean isSubmitted;
    private final boolean isPrintedArrived;
    private final ApplicationType applicationType;
    private final String selfIntroduce;
    private final String studyPlan;
    private final EducationalStatus educationalStatus;
}
