package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
}
