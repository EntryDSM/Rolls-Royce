package kr.hs.entrydsm.rollsroyce.domain.auth.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidUrlException extends RollsException {
    public static final RollsException EXCEPTION = new InvalidUrlException();

    private InvalidUrlException() {
        super(ErrorCode.INVALID_URL);
    }
}
