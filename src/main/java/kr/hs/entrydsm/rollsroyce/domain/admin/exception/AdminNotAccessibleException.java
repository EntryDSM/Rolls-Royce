package kr.hs.entrydsm.rollsroyce.domain.admin.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class AdminNotAccessibleException extends RollsException {

    public static final RollsException EXCEPTION =
            new AdminNotAccessibleException();

    private AdminNotAccessibleException() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }

}
