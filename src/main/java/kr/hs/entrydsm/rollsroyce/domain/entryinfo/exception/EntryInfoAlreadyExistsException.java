package kr.hs.entrydsm.rollsroyce.domain.entryinfo.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class EntryInfoAlreadyExistsException extends RollsException {
    public static final RollsException EXCEPTION =
            new EntryInfoAlreadyExistsException();

    private EntryInfoAlreadyExistsException() {
        super(ErrorCode.ENTRY_INFO_ALREADY_EXISTS);
    }
}
