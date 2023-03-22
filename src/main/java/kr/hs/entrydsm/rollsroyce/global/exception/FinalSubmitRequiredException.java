package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class FinalSubmitRequiredException extends RollsException {

    public static final RollsException EXCEPTION = new FinalSubmitRequiredException();

    private FinalSubmitRequiredException() {
        super(ErrorCode.FINAL_SUBMIT_REQUIRED);
    }
}
