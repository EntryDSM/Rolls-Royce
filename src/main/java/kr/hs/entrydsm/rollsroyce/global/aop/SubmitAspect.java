package kr.hs.entrydsm.rollsroyce.global.aop;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SubmitAspect {

	private final UserFacade userFacade;
	private final StatusFacade statusFacade;

	@Around("applicationController()")
	public Object checkSubmit(ProceedingJoinPoint joinPoint) throws Throwable {
		if(statusFacade.getStatusByReceiptCode(
				userFacade.getCurrentReceiptCode()
		).getIsSubmitted().equals(Boolean.TRUE))
			throw AlreadySubmitException.EXCEPTION;
		return joinPoint.proceed();
	}

	@Pointcut("within(kr.hs.entrydsm.rollsroyce.domain.application.presentation.*)")
	public void applicationController() {}

}
