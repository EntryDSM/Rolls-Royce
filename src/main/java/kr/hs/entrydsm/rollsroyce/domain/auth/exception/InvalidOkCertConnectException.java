package kr.hs.entrydsm.rollsroyce.domain.auth.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidOkCertConnectException extends RollsException {

    public static final RollsException EXCEPTION = new InvalidOkCertConnectException();

    private InvalidOkCertConnectException() {
        super(ErrorCode.INVALID_OKCERT_CONNECTION);
    }
}
