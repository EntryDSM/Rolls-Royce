package kr.hs.entrydsm.rollsroyce.domain.application.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class EducationalStatusUnmatchedException extends RollsException {

	public static RollsException EXCEPTION =
			new EducationalStatusUnmatchedException();

	private EducationalStatusUnmatchedException() {
		super(ErrorCode.EDUCATIONAL_STATUS_UNMATCHED);
	}

}
