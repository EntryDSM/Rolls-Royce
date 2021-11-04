package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ScoreNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new ScoreNotFoundException();

	private ScoreNotFoundException() {
		super(ErrorCode.SCORE_NOT_FOUND);
	}

}
