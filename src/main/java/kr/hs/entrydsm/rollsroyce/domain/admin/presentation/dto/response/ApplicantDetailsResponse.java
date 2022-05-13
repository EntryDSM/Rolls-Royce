package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ApplicantDetailsResponse {

    private final Status status;
    private final CommonInformation commonInformation;
    private final MoreInformation moreInformation;
    private final Evaluation evaluation;

    @Getter
    @AllArgsConstructor
    public static class Status {
        private final Boolean isPrintsArrived;
        private final Boolean isSubmitted;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommonInformation {
        private final String name;
        private final String schoolName;
        private final String email;
        private final String telephoneNumber;
        private final String schoolTel;
        private final String parentTel;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MoreInformation {
        private final String photoUrl;
        private final String birthday;
        private final String educationStatus;
        private final String applicationType;
        private final String applicationRemark;
        private final String address;
        private final String detailAddress;
        private final String headCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Evaluation {
        private final Integer volunteerTime;
        private final BigDecimal conversionScore;
        private final Integer dayAbsenceCount;
        private final Integer lectureAbsenceCount;
        private final Integer earlyLeaveCount;
        private final Integer latenessCount;
        private final BigDecimal averageScore;
        private final String selfIntroduce;
        private final String studyPlan;
    }


}
