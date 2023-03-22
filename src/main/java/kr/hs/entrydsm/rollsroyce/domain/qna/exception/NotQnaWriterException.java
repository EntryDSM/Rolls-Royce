package kr.hs.entrydsm.rollsroyce.domain.qna.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class NotQnaWriterException extends RollsException {
    public static final RollsException EXCEPTION =
            new NotQnaWriterException();

    private NotQnaWriterException() {
        super(ErrorCode.NOT_QNA_WRITER);
    }
}
