package kr.hs.entrydsm.rollsroyce.domain.auth.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class PassInfoNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new PassInfoNotFoundException();

    private PassInfoNotFoundException() {
        super(ErrorCode.PASS_INFO_NOT_FOUND);
    }
}
