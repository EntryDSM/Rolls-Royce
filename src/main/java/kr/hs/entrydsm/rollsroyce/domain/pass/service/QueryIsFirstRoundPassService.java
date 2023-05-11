package kr.hs.entrydsm.rollsroyce.domain.pass.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.pass.exception.AdmissionUnavailableException;
import kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response.QueryIsFirstRoundPassResponse;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;

@RequiredArgsConstructor
@Service
public class QueryIsFirstRoundPassService {
    private final StatusFacade statusFacade;
    private final ScheduleFacade scheduleFacade;
    private final EntryInfoFacade entryInfoFacade;

    public QueryIsFirstRoundPassResponse execute() {
        Schedule firstAnnounce = scheduleFacade.getScheduleByType(Type.FIRST_ANNOUNCEMENT);
        if (LocalDateTime.now().isBefore(firstAnnounce.getDate())) {
            throw AdmissionUnavailableException.EXCEPTION;
        }

        Status status = statusFacade.getStatusByReceiptCode(entryInfoFacade.getCurrentReceiptCode());

        return QueryIsFirstRoundPassResponse.builder()
                .isFirstRoundPass(status.isFirstRoundPass())
                .build();
    }
}
