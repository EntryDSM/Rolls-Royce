package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AuthCodeAlreadyVerifiedException extends RollsException {

    public static RollsException EXCEPTION =
            new AuthCodeAlreadyVerifiedException();

    private AuthCodeAlreadyVerifiedException() {
        super(ErrorCode.AUTH_CODE_ALREADY_VERIFIED);
    }

}
