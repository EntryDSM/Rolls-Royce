package kr.hs.entrydsm.rollsroyce.domain.entryinfo.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ApplicationNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new ApplicationNotFoundException();

	private ApplicationNotFoundException() {
		super(ErrorCode.APPLICATION_NOT_FOUND);
	}

}
