package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ApplicationCountNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new ApplicationCountNotFoundException();

	private ApplicationCountNotFoundException() {
		super(ErrorCode.APPLICATION_COUNT_NOT_FOUND);
	}

}
