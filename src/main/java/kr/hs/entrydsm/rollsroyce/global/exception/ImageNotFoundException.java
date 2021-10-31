package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class ImageNotFoundException extends RollsException {

	public static RollsException EXCEPTION =
			new ImageNotFoundException();

	private ImageNotFoundException() {
		super(ErrorCode.IMAGE_NOT_FOUND);
	}

}
