package kr.hs.entrydsm.rollsroyce.domain.schedule.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ScheduleNotFoundException extends RollsException {

    public static RollsException EXCEPTION =
            new ScheduleNotFoundException();

    public ScheduleNotFoundException() {
        super(ErrorCode.SCHEDULE_NOT_FOUND);
    }
}
