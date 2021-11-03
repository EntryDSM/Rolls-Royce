package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class UserNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new UserNotFoundException();

	private UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}

}
