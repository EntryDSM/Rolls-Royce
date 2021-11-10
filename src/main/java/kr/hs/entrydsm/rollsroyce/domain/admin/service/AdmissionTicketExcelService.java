package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdmissionTicketExcelService {

    private final ScheduleFacade scheduleFacade;
    private final AdminFacade adminFacade;

    public void execute() {
        adminFacade.getRootAdmin();

        if (!scheduleFacade.getScheduleByType(Type.END_DATE)
				.isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }
        
    }

}
