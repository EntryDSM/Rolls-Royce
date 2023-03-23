package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class MessageRejectedException extends RollsException {

    public static RollsException EXCEPTION = new MessageRejectedException();

    private MessageRejectedException() {
        super(ErrorCode.MESSAGE_REJECTED);
    }
}
