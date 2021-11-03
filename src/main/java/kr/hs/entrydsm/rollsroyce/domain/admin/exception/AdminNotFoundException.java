package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AdminNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new AdminNotFoundException();

	private AdminNotFoundException() {
		super(ErrorCode.ADMIN_NOT_FOUND);
	}

}
