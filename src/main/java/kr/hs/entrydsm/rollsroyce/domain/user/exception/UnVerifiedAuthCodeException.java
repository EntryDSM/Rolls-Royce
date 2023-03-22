package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class UnVerifiedAuthCodeException extends RollsException {

    public static final RollsException EXCEPTION = new UnVerifiedAuthCodeException();

    private UnVerifiedAuthCodeException() {
        super(ErrorCode.UNVERIFIED_AUTH_CODE);
    }
}
