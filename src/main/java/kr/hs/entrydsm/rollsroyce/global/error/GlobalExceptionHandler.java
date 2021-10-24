package kr.hs.entrydsm.rollsroyce.global.error;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RollsException.class)
	public ResponseEntity<ErrorResponse> handlingRollsException(RollsException e) {
		ErrorCode code = e.getErrorCode();
		return new ResponseEntity<>(new ErrorResponse(code.getStatus(), code.getCode(), code.getMessage()),
				HttpStatus.valueOf(code.getStatus()));
	}

}
