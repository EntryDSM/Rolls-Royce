package kr.hs.entrydsm.rollsroyce.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RollsException extends RuntimeException {

	private final ErrorCode errorCode;

}
