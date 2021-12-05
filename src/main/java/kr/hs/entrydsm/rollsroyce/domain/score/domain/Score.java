package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_calculated_score")
public class Score {

	@Id
	private Long receiptCode;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receipt_code")
	private User user;

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

	public Score(User user, ApplicationCase applicationCase) {
		this.user = user;
		this.volunteerScore = applicationCase.calculateVolunteerScore();
		this.attendanceScore = applicationCase.calculateAttendanceScore();

		BigDecimal[] gradeScores = applicationCase.calculateGradeScores();
		this.thirdBeforeBeforeScore = gradeScores[0];
		this.thirdBeforeScore = gradeScores[1];
		this.thirdGradeScore = gradeScores[2];

		this.totalGradeScore = applicationCase.calculateTotalGradeScore();
		this.totalScore = volunteerScore.add(BigDecimal.valueOf(attendanceScore)).add(totalGradeScore);
	}

}
