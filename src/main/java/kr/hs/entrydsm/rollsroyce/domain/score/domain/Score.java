package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "tbl_calculated_score")
public class Score {

	@Id
	private long receiptCode;

	@Digits(integer = 2, fraction = 3)
	private BigDecimal volunteerScore;

	private int attendanceScore;

	@Digits(integer = 2, fraction = 3)
	private BigDecimal thirdBeforeBeforeScore;

	@Digits(integer = 2, fraction = 3)
	private BigDecimal thirdBeforeScore;

	@Digits(integer = 2, fraction = 3)
	private BigDecimal thirdGradeScore;

	@Digits(integer = 3, fraction = 3)
	private BigDecimal totalGradeScore;

	@Digits(integer = 3, fraction = 3)
	private BigDecimal totalScore;

}
