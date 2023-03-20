package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ScoreVo {
    private final BigDecimal thirdGradeScore;
    private final BigDecimal thirdBeforeScore;
    private final BigDecimal thirdBeforeBeforeScore;
    private final BigDecimal volunteerScore;
    private final BigDecimal totalGradeScore;
    private final Integer attendanceScore;
    private final BigDecimal totalScore;

    @QueryProjection
    public ScoreVo(Score score) {
        this.thirdGradeScore = score.getThirdGradeScore();
        this.thirdBeforeScore = score.getThirdBeforeScore();
        this.thirdBeforeBeforeScore = score.getThirdBeforeBeforeScore();
        this.volunteerScore = score.getVolunteerScore();
        this.totalGradeScore = score.getTotalGradeScore();
        this.attendanceScore = score.getAttendanceScore();
        this.totalScore = score.getTotalScore();
    }
}
