package kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StatusResponse {
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final boolean isSubmitted;
    private final boolean isPrintedArrived;
    private final ApplicationType applicationType;
    private final String selfIntroduce;
    private final String studyPlan;
}
