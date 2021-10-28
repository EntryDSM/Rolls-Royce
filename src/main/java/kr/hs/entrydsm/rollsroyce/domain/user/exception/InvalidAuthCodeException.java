package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidAuthCodeException extends RollsException {

    public static RollsException EXCEPTION =
            new InvalidAuthCodeException();

    private InvalidAuthCodeException() {
        super(ErrorCode.INVALID_AUTH_CODE);
    }

}
