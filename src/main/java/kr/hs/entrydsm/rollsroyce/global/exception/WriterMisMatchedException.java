package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class WriterMisMatchedException extends RollsException {
    public static final RollsException EXCEPTION = new WriterMisMatchedException();

    public WriterMisMatchedException() {
        super(ErrorCode.WRITER_MISMATCHED);
    }
}
