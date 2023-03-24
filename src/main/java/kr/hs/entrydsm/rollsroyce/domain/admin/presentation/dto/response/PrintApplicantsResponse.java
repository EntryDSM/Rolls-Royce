package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PrintApplicantsResponse {
    private final List<ApplicantDto> applicants;
    private final List<GraduationDto> graduations;
    private final List<SchoolDto> schools;
    private final List<GraduationCaseDto> graduationCases;
    private final List<ScoreDto> scores;

    @Getter
    @Builder
    public static class ApplicantDto {
        private final Long receiptCode; // 접수번호
        private final String educationalStatus; // 학력
        private final String applicationType; // 접수 유형
        private final String name; // 이름
        private final Boolean isDaejeon; // 지역
        private final String birthday; // 생일
        private final String telephoneNumber; // 전화번호
        private final String applicationRemark; // 특기사항
        private final String sex; // 성별
        private final String parentTel; // 부모님 전화번호
    }

    @Getter
    @Builder
    public static class GraduationDto {
        private final LocalDate graduatedAt; // 졸업일
        private final String studentNumber; // 학번
    }

    @Getter
    @Builder
    public static class SchoolDto {
        private final String schoolName; // 출신 중학교 이름
    }

    @Getter
    @Builder
    public static class GraduationCaseDto {
        private final String koreanGrade; // 국어 점수
        private final String socialGrade; // 사회 점수
        private final String historyGrade; // 역사 점수
        private final String mathGrade; // 수학 점수
        private final String scienceGrade; // 과학 점수
        private final String techAndHomeGrade; // 기술가정 점수
        private final String englishGrade; // 영어 점수
        private final Integer volunteerTime; // 봉사시간
        private final Integer latenessCount; // 무단 지각
        private final Integer dayAbsenceCount; // 무단 결석
        private final Integer earlyLeaveCount; // 무단 조퇴
        private final Integer lectureAbsenceCount; // 무단 결과
    }

    @Getter
    @Builder
    public static class ScoreDto {
        private final BigDecimal thirdGradeScore; // 최근학기 성적 점수
        private final BigDecimal thirdBeforeScore; // 직전 학기 성적 점수
        private final BigDecimal thirdBeforeBeforeScore; // 직전전 학기 성적 점수
        private final BigDecimal volunteerScore; // 봉사 점수
        private final BigDecimal totalGradeScore; // 성적 총합
        private final Integer attendanceScore; // 출석 점수
        private final BigDecimal totalScore; // 총점
    }
}
