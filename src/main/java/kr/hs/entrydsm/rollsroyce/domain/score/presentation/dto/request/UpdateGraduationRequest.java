package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateGraduationRequest {

    @PositiveOrZero
    private int volunteerTime;

    @PositiveOrZero
    private int dayAbsenceCount;

    @PositiveOrZero
    private int lectureAbsenceCount;

    @PositiveOrZero
    private int latenessCount;

    @PositiveOrZero
    private int earlyLeaveCount;

    @Pattern(regexp = "[A-E,X]{4}")
    private String koreanGrade;

    @Pattern(regexp = "[A-E,X]{4}")
    private String socialGrade;

    @Pattern(regexp = "[A-E,X]{4}")
    private String historyGrade;

    @Pattern(regexp = "[A-E,X]{4}")
    private String mathGrade;

    @Pattern(regexp = "[A-E,X]{4}")
    private String scienceGrade;

    @Pattern(regexp = "[A-E,X]{4}")
    private String englishGrade;

    @Pattern(regexp = "[A-E,X]{4}")
    private String techAndHomeGrade;

}
