package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidKeywordException extends RollsException {

    public static final RollsException EXCEPTION = new InvalidKeywordException();

    private InvalidKeywordException() {
        super(ErrorCode.INVALID_KEYWORD);
    }
}
