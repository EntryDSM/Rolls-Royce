package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class NewApplicantsResponse {
    private final List<Applicant> applicants;
    private final List<Graduation> graduations;
    private final List<School> schools;
    private final List<GraduationCase> graduationCases;
    private final List<Score> scores;

    @Getter
    @Builder
    public static class Applicant {
        //접수번호, 학력, 접수 유형, 이름, 지역, 생일, 전화번호, 특기사항, 성별, 부모님 전화번호
        private final Long receiptCode;
        private final String educationalStatus;
        private final String applicationType;
        private final String name;
        private final Boolean isDaejeon;
        private final String birthday;
        private final String telephoneNumber;
        private final String applicationRemark;
        private final String sex;
        private final String parentTel;
    }

    @Getter
    @Builder
    public static class Graduation {
        //졸업 년도, 학번,
        private final LocalDate graduatedAt;
        private final String studentNumber;
    }

    @Getter
    @Builder
    public static class School {
        //출신 중학교 이름
        private final String schoolName;
    }

    @Getter
    @Builder
    public static class GraduationCase {
        //국어, 사회, 역사, 수학, 과학, 기술가정, 영어, 봉사시간, 무단 지각, 무단 결석, 무단 조퇴, 무단 결과
        private final String koreanGrade;
        private final String socialGrade;
        private final String historyGrade;
        private final String mathGrade;
        private final String scienceGrade;
        private final String techAndHomeGrade;
        private final String englishGrade;
        private final Integer volunteerTime;
        private final Integer latenessCount;
        private final Integer dayAbsenceCount;
        private final Integer earlyLeaveCount;
        private final Integer lectureAbsenceCount;
    }

    @Getter
    @Builder
    public static class Score {
        //직전학기, 직전전 학기, 직전전전 학기, 봉사 점수, 성적 총합, 출석 점수, 총점
        private final BigDecimal thirdGradeScore;
        private final BigDecimal thirdBeforeScore;
        private final BigDecimal thirdBeforeBeforeScore;
        private final BigDecimal volunteerScore;
        private final BigDecimal totalGradeScore;
        private final Integer attendanceScore;
        private final BigDecimal totalScore;
    }
}
