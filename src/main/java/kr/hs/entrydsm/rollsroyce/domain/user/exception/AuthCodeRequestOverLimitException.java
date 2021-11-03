package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AuthCodeRequestOverLimitException extends RollsException {

    public static final RollsException EXCEPTION =
            new AuthCodeRequestOverLimitException();

    private AuthCodeRequestOverLimitException() {
        super(ErrorCode.AUTH_CODE_REQUEST_OVER_LIMIT);
    }

}
