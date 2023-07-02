package kr.hs.entrydsm.rollsroyce.domain.question.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ReplyNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new ReplyNotFoundException();

    public ReplyNotFoundException() {
        super(ErrorCode.REPLY_NOT_FOUND);
    }
}
