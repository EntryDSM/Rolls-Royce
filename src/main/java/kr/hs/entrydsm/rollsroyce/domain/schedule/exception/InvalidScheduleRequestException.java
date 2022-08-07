package kr.hs.entrydsm.rollsroyce.domain.schedule.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidScheduleRequestException extends RollsException {

    public static final InvalidScheduleRequestException EXCEPTION
            = new InvalidScheduleRequestException();

    private InvalidScheduleRequestException() {
        super(ErrorCode.INVALID_SCHEDULE);
    }

}
