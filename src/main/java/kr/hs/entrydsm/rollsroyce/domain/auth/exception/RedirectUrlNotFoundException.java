package kr.hs.entrydsm.rollsroyce.domain.auth.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class RedirectUrlNotFoundException extends RollsException {
    public static final RollsException EXCEPTION
            = new RedirectUrlNotFoundException();

    private RedirectUrlNotFoundException() {
        super(ErrorCode.URL_NOT_FOUND);
    }
}
