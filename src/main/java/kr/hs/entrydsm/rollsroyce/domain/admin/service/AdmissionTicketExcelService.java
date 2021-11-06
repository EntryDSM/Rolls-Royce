package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AdmissionTicketExcelService {

    private final ScheduleRepository scheduleRepository;

    private final AdminFacade adminFacade;
    private final AdminAuthenticationFacade authenticationFacade;

    public void execute() {
        adminFacade.getAdminById(authenticationFacade.getEmail());

        if (!scheduleRepository.findByType(Type.END_DATE).getDate().isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }
        
    }

}
