package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidKeywordException extends RollsException {

    public static RollsException EXCEPTION =
            new InvalidKeywordException();

    public InvalidKeywordException() {
        super(ErrorCode.INVALID_KEYWORD);
    }
}
