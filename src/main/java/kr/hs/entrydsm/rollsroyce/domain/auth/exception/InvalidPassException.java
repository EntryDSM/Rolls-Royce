package kr.hs.entrydsm.rollsroyce.domain.auth.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidPassException extends RollsException {
    public static final RollsException EXCEPTION = new InvalidPassException();

    private InvalidPassException() {
        super(ErrorCode.INVALID_PASS);
    }
}
