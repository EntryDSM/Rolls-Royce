package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ApplicationPeriodNotOverException extends RollsException {

    public static RollsException EXCEPTION =
            new ApplicationPeriodNotOverException();

    public ApplicationPeriodNotOverException() {
        super(ErrorCode.APPLICATION_PERIOD_NOT_OVER);
    }

}
