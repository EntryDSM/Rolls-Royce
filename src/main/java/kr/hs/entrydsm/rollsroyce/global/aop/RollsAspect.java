package kr.hs.entrydsm.rollsroyce.global.aop;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidDateException;

@Aspect
@RequiredArgsConstructor
@Component
public class RollsAspect {

    private final UserFacade userFacade;
    private final StatusFacade statusFacade;
    private final ScheduleFacade scheduleFacade;

    @Around("applicationController() && scoreController()")
    public Object checkSubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        if (statusFacade
                .getStatusByReceiptCode(userFacade.getCurrentReceiptCode())
                .getIsSubmitted()
                .equals(Boolean.TRUE)) throw AlreadySubmitException.EXCEPTION;
        return joinPoint.proceed();
    }

    @Around("applicationController() && scoreController()")
    public Object checkSchedule(ProceedingJoinPoint joinPoint) throws Throwable {
        Schedule startDate = scheduleFacade.getScheduleByType(Type.START_DATE);
        Schedule endDate = scheduleFacade.getScheduleByType(Type.END_DATE);
        if (!startDate.isBefore(LocalDateTime.now()) || !endDate.isAfter(LocalDateTime.now()))
            throw InvalidDateException.EXCEPTION;
        return joinPoint.proceed();
    }

    @Pointcut("within(kr.hs.entrydsm.rollsroyce.domain.application.presentation.*)")
    public void applicationController() {}

    @Pointcut("within(kr.hs.entrydsm.rollsroyce.domain.score.*)")
    public void scoreController() {}
}
