package kr.hs.entrydsm.rollsroyce.domain.school.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class SchoolNotFoundException extends RollsException {

	public static RollsException EXCEPTION =
			new SchoolNotFoundException();

	private SchoolNotFoundException() {
		super(ErrorCode.SCHOOL_NOT_FOUND);
	}

}
