package kr.hs.entrydsm.rollsroyce.domain.qna.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AccessDeniedQnaException extends RollsException {
    public static final RollsException EXCEPTION = new AccessDeniedQnaException();

    private AccessDeniedQnaException() {
        super(ErrorCode.ACCESS_DENIED_QNA);
    }
}
