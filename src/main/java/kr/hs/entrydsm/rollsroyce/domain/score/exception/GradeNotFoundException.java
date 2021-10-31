package kr.hs.entrydsm.rollsroyce.domain.score.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class GradeNotFoundException extends RollsException {

    public static RollsException EXCEPTION =
            new GradeNotFoundException();

    public GradeNotFoundException() {
        super(ErrorCode.GRADE_NOT_FOUND);
    }
}
