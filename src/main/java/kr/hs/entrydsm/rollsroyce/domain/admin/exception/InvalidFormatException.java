package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidFormatException extends RollsException {
    public static final RollsException EXCEPTION = new InvalidFormatException();

    private InvalidFormatException() {
        super(ErrorCode.INVALID_FORMAT);
    }
}
