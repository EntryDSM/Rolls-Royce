package kr.hs.entrydsm.rollsroyce.domain.application.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ProcessNotCompletedException extends RollsException {

    public static RollsException EXCEPTION = new ProcessNotCompletedException();

    private ProcessNotCompletedException() {
        super(ErrorCode.PROCESS_NOT_COMPLETED);
    }
}
