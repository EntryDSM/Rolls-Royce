package kr.hs.entrydsm.rollsroyce.domain.screen.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ScreenNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new ScreenNotFoundException();

    public ScreenNotFoundException() {
        super(ErrorCode.SCREEN_NOT_FOUND);
    }
}
