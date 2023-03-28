package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class PasswordNotValidException extends RollsException {

    public static final RollsException EXCEPTION = new PasswordNotValidException();

    private PasswordNotValidException() {
        super(ErrorCode.INVALID_ADMIN_PASSWORD);
    }
}
