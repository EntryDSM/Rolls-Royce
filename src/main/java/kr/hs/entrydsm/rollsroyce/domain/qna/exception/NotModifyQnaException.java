package kr.hs.entrydsm.rollsroyce.domain.qna.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class NotModifyQnaException extends RollsException {
    public static final RollsException EXCEPTION =
            new NotModifyQnaException();

    private NotModifyQnaException() {
        super(ErrorCode.NOT_MODIFY_QNA);
    }
}
