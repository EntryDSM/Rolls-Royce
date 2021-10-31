package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ExcelOException extends RollsException {
    public static RollsException EXCEPTION =
            new ExcelOException();

    public ExcelOException() {
        super(ErrorCode.EXCEL_IO_EXCEPTION);
    }
}
