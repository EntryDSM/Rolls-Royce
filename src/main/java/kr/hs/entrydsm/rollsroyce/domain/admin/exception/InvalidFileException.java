package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidFileException extends RollsException {

    public static final RollsException EXCEPTION = new InvalidFileException();

    private InvalidFileException() {
        super(ErrorCode.INVALID_FILE);
    }
}
