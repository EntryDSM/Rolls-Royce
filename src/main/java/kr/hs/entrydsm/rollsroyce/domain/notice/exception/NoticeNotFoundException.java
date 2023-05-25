package kr.hs.entrydsm.rollsroyce.domain.notice.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class NoticeNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new NoticeNotFoundException();

    private NoticeNotFoundException() {
        super(ErrorCode.NOTICE_NOT_FOUND);
    }
}
