package kr.hs.entrydsm.rollsroyce.domain.entryInfo.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidEnumConstantException extends RollsException {

	public static final RollsException EXCEPTION =
			new InvalidEnumConstantException();

	private InvalidEnumConstantException() {
		super(ErrorCode.INVALID_ENUM_CONSTANT);
	}

}
