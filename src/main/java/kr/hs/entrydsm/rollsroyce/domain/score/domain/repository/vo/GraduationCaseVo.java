package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import lombok.Getter;

@Getter
public class GraduationCaseVo {
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

    @QueryProjection
    public GraduationCaseVo(GraduationCase graduationCase) {
        this.koreanGrade = graduationCase.getKoreanGrade();
        this.socialGrade = graduationCase.getSocialGrade();
        this.historyGrade = graduationCase.getHistoryGrade();
        this.mathGrade = graduationCase.getMathGrade();
        this.scienceGrade = graduationCase.getScienceGrade();
        this.techAndHomeGrade = graduationCase.getTechAndHomeGrade();
        this.englishGrade = graduationCase.getEnglishGrade();
        this.volunteerTime = graduationCase.getVolunteerTime();
        this.latenessCount = graduationCase.getLatenessCount();
        this.dayAbsenceCount = graduationCase.getDayAbsenceCount();
        this.earlyLeaveCount = graduationCase.getEarlyLeaveCount();
        this.lectureAbsenceCount = graduationCase.getLectureAbsenceCount();
    }
}
