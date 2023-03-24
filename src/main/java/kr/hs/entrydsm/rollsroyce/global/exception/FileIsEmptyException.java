package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class FileIsEmptyException extends RollsException {

    public static RollsException EXCEPTION = new FileIsEmptyException();

    private FileIsEmptyException() {
        super(ErrorCode.FILE_IS_EMPTY);
    }
}
