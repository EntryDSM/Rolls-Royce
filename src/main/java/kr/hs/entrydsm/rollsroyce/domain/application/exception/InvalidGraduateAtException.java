package kr.hs.entrydsm.rollsroyce.domain.application.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidGraduateAtException extends RollsException {

    public static final RollsException EXCEPTION = new InvalidGraduateAtException();

    private InvalidGraduateAtException() {
        super(ErrorCode.INVALID_GRADUATE_AT);
    }
}
