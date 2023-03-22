package kr.hs.entrydsm.rollsroyce.domain.schedule.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ScheduleNotFoundException extends RollsException {

    public static final RollsException EXCEPTION = new ScheduleNotFoundException();

    private ScheduleNotFoundException() {
        super(ErrorCode.SCHEDULE_NOT_FOUND);
    }
}
