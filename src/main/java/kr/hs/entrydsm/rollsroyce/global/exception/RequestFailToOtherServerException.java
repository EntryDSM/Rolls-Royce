package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class RequestFailToOtherServerException extends RollsException {

    public static final RollsException EXCEPTION = new RequestFailToOtherServerException();

    private RequestFailToOtherServerException() {
        super(ErrorCode.REQUEST_FAIL_TO_OTHER_SERVER);
    }
}
