package kr.hs.entrydsm.rollsroyce.domain.score.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ApplicationTypeUnmatchedException extends RollsException {

    public static RollsException EXCEPTION =
            new ApplicationTypeUnmatchedException();

    private ApplicationTypeUnmatchedException() {
        super(ErrorCode.APPLICATION_TYPE_UNMATCHED);
    }
}
