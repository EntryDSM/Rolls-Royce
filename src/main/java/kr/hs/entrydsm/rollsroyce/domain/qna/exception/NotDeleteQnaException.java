package kr.hs.entrydsm.rollsroyce.domain.qna.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class NotDeleteQnaException extends RollsException {
    public static final RollsException EXCEPTION =
            new NotDeleteQnaException();

    private NotDeleteQnaException() {
        super(ErrorCode.NOT_DELETE_QNA);
    }
}
