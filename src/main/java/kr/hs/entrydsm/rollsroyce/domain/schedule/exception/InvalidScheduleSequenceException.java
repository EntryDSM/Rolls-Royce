package kr.hs.entrydsm.rollsroyce.domain.schedule.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class InvalidScheduleSequenceException extends RollsException {

    public static final InvalidScheduleSequenceException EXCEPTION = new InvalidScheduleSequenceException();

    public InvalidScheduleSequenceException() { super(ErrorCode.DATE_SEQUENCE_NOT_VALID);}

}
