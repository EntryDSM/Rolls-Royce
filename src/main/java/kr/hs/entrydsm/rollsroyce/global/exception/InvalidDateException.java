package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidDateException extends RollsException {

	public static final RollsException EXCEPTION =
			new InvalidDateException();

	private InvalidDateException() {
		super(ErrorCode.INVALID_DATE);
	}

}
