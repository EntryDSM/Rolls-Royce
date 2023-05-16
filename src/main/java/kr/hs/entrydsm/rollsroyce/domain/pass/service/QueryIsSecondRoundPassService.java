package kr.hs.entrydsm.rollsroyce.domain.pass.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.pass.exception.AdmissionUnavailableException;
import kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response.QueryIsSecondRoundPassResponse;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;

@RequiredArgsConstructor
@Service
public class QueryIsSecondRoundPassService {
    private final StatusFacade statusFacade;
    private final ScheduleFacade scheduleFacade;
    private final EntryInfoFacade entryInfoFacade;

    public QueryIsSecondRoundPassResponse execute() {
        Schedule secondAnnounce = scheduleFacade.getScheduleByType(Type.SECOND_ANNOUNCEMENT);
        if (LocalDateTime.now().isBefore(secondAnnounce.getDate())) {
            throw AdmissionUnavailableException.EXCEPTION;
        }

        Status status = statusFacade.getStatusByReceiptCode(entryInfoFacade.getCurrentReceiptCode());

        return QueryIsSecondRoundPassResponse.builder()
                .isSecondPass(status.isSecondRoundPass())
                .build();
    }
}
