package kr.hs.entrydsm.rollsroyce.domain.question.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AccessDeniedQuestionException extends RollsException {
    public static final RollsException EXCEPTION = new AccessDeniedQuestionException();

    private AccessDeniedQuestionException() {
        super(ErrorCode.ACCESS_DENIED_QUESTION);
    }
}
