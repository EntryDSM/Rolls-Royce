package kr.hs.entrydsm.rollsroyce.domain.status.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AlreadySubmitException extends RollsException {

	public static RollsException EXCEPTION =
			new AlreadySubmitException();

	public AlreadySubmitException() {
		super(ErrorCode.ALREADY_SUBMIT);
	}

}
