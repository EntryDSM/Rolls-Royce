package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_qualification_exam_case")
public class QualificationCase extends ApplicationCase {

    @Digits(integer = 3, fraction = 2)
    private BigDecimal averageScore;

    @Builder
    public QualificationCase(
            BigDecimal averageScore,
            long receiptCode,
            boolean isDaejeon,
            ApplicationType applicationType,
            EducationalStatus educationalStatus) {
        super(receiptCode, isDaejeon, applicationType, educationalStatus);
        this.averageScore = averageScore;
    }

    @Override
    public BigDecimal calculateVolunteerScore() {
        return volunteerScoreFormula();
    }

    @Override
    public Integer calculateAttendanceScore() {
        return MAX_ATTENDANCE_SCORE;
    }

    @Override
    public BigDecimal[] calculateGradeScores() {
        return new BigDecimal[] {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
    }

    @Override
    public BigDecimal calculateTotalGradeScore() {
        return gradeScoreFormula();
    }

    private BigDecimal volunteerScoreFormula() {
        return averageScore.subtract(BigDecimal.valueOf(40)).divide(BigDecimal.valueOf(4), 3, RoundingMode.HALF_UP);
    }

    private BigDecimal gradeScoreFormula() {
        BigDecimal gradeScore = averageScore
                .subtract(BigDecimal.valueOf(50))
                .multiply(BigDecimal.valueOf(1.6))
                .setScale(3, RoundingMode.HALF_UP);

        if (isCommon()) {
            return gradeScore.multiply(COMMON_GRADE_RATE).setScale(3, RoundingMode.HALF_UP);
        } else {
            return gradeScore;
        }
    }
}
