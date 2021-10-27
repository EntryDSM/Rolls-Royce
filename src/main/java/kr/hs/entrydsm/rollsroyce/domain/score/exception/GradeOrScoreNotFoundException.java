package kr.hs.entrydsm.rollsroyce.domain.score.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class GradeOrScoreNotFoundException extends RollsException {

    public static RollsException EXCEPTION =
            new GradeOrScoreNotFoundException();

    public GradeOrScoreNotFoundException() {
        super(ErrorCode.GRADE_OR_SCORE_NOT_FOUND);
    }
}
