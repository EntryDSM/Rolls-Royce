package kr.hs.entrydsm.rollsroyce.domain.user.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class UserWithdrawalNotPossibleException extends RollsException {

    public static final RollsException EXCEPTION = new UserWithdrawalNotPossibleException();

    private UserWithdrawalNotPossibleException() {
        super(ErrorCode.USER_WITHDRAWAL_NOT_POSSIBLE);
    }
}
