package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class CredentialsNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new CredentialsNotFoundException();

	private CredentialsNotFoundException() {
		super(ErrorCode.CREDENTIALS_NOT_FOUND);
	}

}
