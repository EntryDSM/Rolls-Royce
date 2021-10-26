package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class ApplicationCase {

	protected static final BigDecimal COMMON_GRADE_RATE = BigDecimal.valueOf(1.75);

	protected static final int MAX_VOLUNTEER_TIME = 30;
	protected static final int MIN_VOLUNTEER_TIME = 6;

	protected static final int MAX_VOLUNTEER_SCORE = 15;
	protected static final int MIN_VOLUNTEER_SCORE = 3;

	protected static final int MAX_ATTENDANCE_SCORE = 15;

	@Transient
	protected Scorer scorer;

	@Id
	protected Long receiptCode;

	protected ApplicationCase(Scorer scorer) {
		this.scorer = scorer;
		this.receiptCode = scorer.getReceiptCode();
	}

	abstract BigDecimal calculateVolunteerScore();
	abstract Integer calculateAttendanceScore();
	abstract BigDecimal[] calculateGradeScores();
	abstract BigDecimal calculateTotalGradeScore();

}
