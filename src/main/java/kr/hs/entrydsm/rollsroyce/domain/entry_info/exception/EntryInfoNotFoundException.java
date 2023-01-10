package kr.hs.entrydsm.rollsroyce.domain.entry_info.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class EntryInfoNotFoundException extends RollsException {
    public static final RollsException EXCEPTION =
            new EntryInfoNotFoundException();
    private EntryInfoNotFoundException() {
        super(ErrorCode.ENTRY_INFO_NOT_FOUND);
    }
}
