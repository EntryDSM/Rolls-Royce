package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryTypeResponse {

    private final String educationalStatus;
    private final String applicationType;
    private final boolean isDaejeon;
    private final String applicationRemark;
    private String graduatedAt;
    private boolean isGraduated;

    public void setGraduatedAt(String graduatedAt) {
        this.graduatedAt = graduatedAt;
    }

    public void setGraduated(boolean graduated) {
        this.isGraduated = graduated;
    }

}
