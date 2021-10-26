package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import kr.hs.entrydsm.rollsroyce.domain.score.exception.FieldNotExistException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Scorer {

	@Builder
	public static Scorer initializer(long receiptCode,
			boolean isDaejeon,
			ApplicationType applicationType,
			EducationalStatus educationalStatus) {
		Scorer scorer = new Scorer(receiptCode,
				isDaejeon,
				applicationType,
				educationalStatus);
		return nullCheck(scorer);
	}

	private final long receiptCode;

	private final boolean isDaejeon;

	private final ApplicationType applicationType;

	private final EducationalStatus educationalStatus;

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

	public static Scorer nullCheck(Scorer scorer) {
		if(scorer.applicationType == null || scorer.educationalStatus == null)
			throw FieldNotExistException.EXCEPTION;
		return scorer;
	}

}
