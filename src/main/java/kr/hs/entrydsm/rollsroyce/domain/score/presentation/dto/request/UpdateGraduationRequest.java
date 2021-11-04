package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Pattern(regexp = "[A-E,X]{6}")
    private String koreanGrade;

    @Pattern(regexp = "[A-E,X]{6}")
    private String socialGrade;

    @Pattern(regexp = "[A-E,X]{6}")
    private String historyGrade;

    @Pattern(regexp = "[A-E,X]{6}")
    private String mathGrade;

    @Pattern(regexp = "[A-E,X]{6}")
    private String scienceGrade;

    @Pattern(regexp = "[A-E,X]{6}")
    private String englishGrade;

    @Pattern(regexp = "[A-E,X]{6}")
    private String techAndHomeGrade;
}
