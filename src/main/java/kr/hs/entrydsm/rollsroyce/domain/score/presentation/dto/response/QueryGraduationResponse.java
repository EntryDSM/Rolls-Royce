package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import lombok.Getter;

@Getter
public class QueryGraduationResponse {

    private final int volunteerTime;
    private final int dayAbsenceCount;
    private final int lectureAbsenceCount;
    private final int latenessCount;
    private final int earlyLeaveCount;
    private final String koreanGrade;
    private final String socialGrade;
    private final String historyGrade;
    private final String mathGrade;
    private final String scienceGrade;
    private final String englishGrade;
    private final String techAndHomeGrade;

    public QueryGraduationResponse(GraduationCase graduationCase) {
        this.volunteerTime = graduationCase.getVolunteerTime();

        this.dayAbsenceCount = graduationCase.getDayAbsenceCount();
        this.lectureAbsenceCount = graduationCase.getLectureAbsenceCount();
        this.latenessCount = graduationCase.getLatenessCount();
        this.earlyLeaveCount = graduationCase.getEarlyLeaveCount();

        this.koreanGrade = graduationCase.getKoreanGrade();
        this.socialGrade = graduationCase.getSocialGrade();
        this.historyGrade = graduationCase.getHistoryGrade();
        this.mathGrade = graduationCase.getMathGrade();
        this.scienceGrade = graduationCase.getScienceGrade();
        this.englishGrade = graduationCase.getEnglishGrade();
        this.techAndHomeGrade = graduationCase.getTechAndHomeGrade();
    }

}
