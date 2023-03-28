package kr.hs.entrydsm.rollsroyce.domain.status.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AlreadySubmitException extends RollsException {

    public static final RollsException EXCEPTION = new AlreadySubmitException();

    private AlreadySubmitException() {
        super(ErrorCode.ALREADY_SUBMIT);
    }
}
