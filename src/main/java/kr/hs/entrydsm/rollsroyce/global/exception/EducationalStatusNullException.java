package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class EducationalStatusNullException extends RollsException {

    public static final RollsException EXCEPTION = new EducationalStatusNullException();

    private EducationalStatusNullException() {
        super(ErrorCode.EDUCATIONAL_STATUS_NULL);
    }
}
