package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryGraduationResponse {

    private int volunteerTime;
    private int dayAbsenceCount;
    private int lectureAbsenceCount;
    private int latenessCount;
    private int earlyLeaveCount;
    private String koreanGrade;
    private String socialGrade;
    private String historyGrade;
    private String mathGrade;
    private String scienceGrade;
    private String englishGrade;
    private String techAndHomeGrade;

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
