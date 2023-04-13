package kr.hs.entrydsm.rollsroyce.domain.qna.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class QnaWriterMisMatchedException extends RollsException {
    public static final RollsException EXCEPTION = new QnaWriterMisMatchedException();

    private QnaWriterMisMatchedException() {
        super(ErrorCode.WRITER_MISMATCHED);
    }
}
