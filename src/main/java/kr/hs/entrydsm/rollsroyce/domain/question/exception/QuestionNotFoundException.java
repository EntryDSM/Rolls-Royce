package kr.hs.entrydsm.rollsroyce.domain.question.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class QuestionNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new QuestionNotFoundException();

    private QuestionNotFoundException() {
        super(ErrorCode.QUESTION_NOT_FOUND);
    }
}
