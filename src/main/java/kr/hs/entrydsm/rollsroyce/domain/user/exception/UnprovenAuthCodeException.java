package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class UnprovenAuthCodeException extends RollsException {

    public static final RollsException EXCEPTION =
            new UnprovenAuthCodeException();

    private UnprovenAuthCodeException() {
        super(ErrorCode.UNPROVEN_AUTH_CODE);
    }

}
