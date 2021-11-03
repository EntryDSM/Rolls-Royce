package kr.hs.entrydsm.rollsroyce.domain.score.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class FieldNotExistException extends RollsException {

	public static final RollsException EXCEPTION =
			new FieldNotExistException();

	private FieldNotExistException() {
		super(ErrorCode.FIELD_NOT_FOUND);
	}

}
