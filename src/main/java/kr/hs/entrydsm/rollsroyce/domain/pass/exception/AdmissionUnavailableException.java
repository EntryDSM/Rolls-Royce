package kr.hs.entrydsm.rollsroyce.domain.pass.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AdmissionUnavailableException extends RollsException {
    public static final RollsException EXCEPTION = new AdmissionUnavailableException();

    private AdmissionUnavailableException() {
        super(ErrorCode.ADMISSION_UNAVAILABLE);
    }
}
