package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ExpiredTokenException extends RollsException {

	public static RollsException EXCEPTION =
			new ExpiredTokenException();

	private ExpiredTokenException() {
		super(ErrorCode.EXPIRED_TOKEN);
	}

}
