package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@MappedSuperclass
public abstract class ApplicationCase {

	protected static final BigDecimal COMMON_GRADE_RATE = BigDecimal.valueOf(1.75);

	protected static final int MAX_VOLUNTEER_TIME = 30;
	protected static final int MIN_VOLUNTEER_TIME = 6;

	protected static final int MAX_VOLUNTEER_SCORE = 15;
	protected static final int MIN_VOLUNTEER_SCORE = 3;

	protected static final int MAX_ATTENDANCE_SCORE = 15;

	@Id
	protected Long receiptCode;
	protected boolean isDaejeon;
	protected ApplicationType applicationType;
	protected EducationalStatus educationalStatus;

	protected ApplicationCase(Long receiptCode,
							  boolean isDaejeon,
							  ApplicationType applicationType,
							  EducationalStatus educationalStatus) {
		this.receiptCode = receiptCode;
		this.isDaejeon = isDaejeon;
		this.applicationType = applicationType;
		this.educationalStatus = educationalStatus;
	}

	abstract BigDecimal calculateVolunteerScore();
	abstract Integer calculateAttendanceScore();
	abstract BigDecimal[] calculateGradeScores();
	abstract BigDecimal calculateTotalGradeScore();

	public boolean isDaejeon() {
		return isDaejeon;
	}

	public boolean isMeister() {
		return applicationType == ApplicationType.MEISTER;
	}

	public boolean isCommon() {
		return applicationType == ApplicationType.COMMON;
	}

	public boolean isQualificationExam() {
		return educationalStatus == EducationalStatus.QUALIFICATION_EXAM;
	}

	public boolean isProspectiveGraduate() {
		return educationalStatus == EducationalStatus.PROSPECTIVE_GRADUATE;
	}

	public boolean isGraduated() {
		return educationalStatus == EducationalStatus.GRADUATE;
	}

}
