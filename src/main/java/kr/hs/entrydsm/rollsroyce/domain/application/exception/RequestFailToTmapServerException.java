package kr.hs.entrydsm.rollsroyce.domain.application.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class RequestFailToTmapServerException extends RollsException {

    public static final RollsException EXCEPTION = new RequestFailToTmapServerException();

    private RequestFailToTmapServerException() {
        super(ErrorCode.REQUEST_FAIL_TO_TMAP_SERVER);
    }
}
