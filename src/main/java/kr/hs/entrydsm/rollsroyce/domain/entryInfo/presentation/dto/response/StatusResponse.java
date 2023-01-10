package kr.hs.entrydsm.rollsroyce.domain.entryInfo.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.types.EducationalStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatusResponse {

    private final String name;

    private final String phoneNumber;

    private final boolean isSubmitted;

    private final boolean isPrintedArrived;

    private final ApplicationType applicationType;

    private final String selfIntroduce;

    private final String studyPlan;

    private final EducationalStatus educationalStatus;

}