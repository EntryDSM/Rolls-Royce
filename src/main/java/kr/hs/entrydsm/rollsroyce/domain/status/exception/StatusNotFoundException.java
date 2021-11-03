package kr.hs.entrydsm.rollsroyce.domain.status.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class StatusNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new StatusNotFoundException();

	private StatusNotFoundException() {
		super(ErrorCode.STATUS_NOT_FOUND);
	}

}
