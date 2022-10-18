package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InternalServerErrorException extends RollsException {

    public static final RollsException EXCEPTION =
            new InternalServerErrorException();

    private InternalServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
