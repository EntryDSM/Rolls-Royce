package kr.hs.entrydsm.rollsroyce.domain.qna.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class QnaNotFoundException extends RollsException {
    public static final RollsException EXCEPTION =
            new QnaNotFoundException();

    private QnaNotFoundException() {
        super(ErrorCode.QNA_NOT_FOUND);
    }
}
