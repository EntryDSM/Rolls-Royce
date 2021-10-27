package kr.hs.entrydsm.rollsroyce.global.aop;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SubmitAspect {

	private final UserFacade userFacade;
	private final StatusFacade statusFacade;

	@Around("execution(* kr.hs.entrydsm.rollsroyce.domain.application.presentation.ApplicationController.*(..))")
	public void checkSubmit(JoinPoint joinPoint) {
		if(statusFacade.getStatusByReceiptCode(
				userFacade.getCurrentReceiptCode()
		).getIsSubmitted().equals(Boolean.TRUE))
			throw AlreadySubmitException.EXCEPTION;
	}

}
