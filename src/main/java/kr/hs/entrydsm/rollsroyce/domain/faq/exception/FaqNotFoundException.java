package kr.hs.entrydsm.rollsroyce.domain.faq.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class FaqNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new FaqNotFoundException();

    private FaqNotFoundException() {
        super(ErrorCode.FAQ_NOT_FOUND);
    }
}
