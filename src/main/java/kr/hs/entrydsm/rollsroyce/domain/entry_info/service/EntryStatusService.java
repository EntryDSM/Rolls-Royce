package kr.hs.entrydsm.rollsroyce.domain.entry_info.service;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.presentation.dto.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EntryStatusService {

private final StatusFacade statusFacade;

private final EntryInfoFacade entryInfoFacade;

public StatusResponse execute() {
		final EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
    final Status status = statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode());

    return StatusResponse.builder()
            .phoneNumber(entryInfo.getUserTelephoneNumber())
            .name(entryInfo.getUserName())
            .isSubmitted(status.getIsSubmitted())
            .isPrintedArrived(status.getIsPrintsArrived())
            .applicationType(entryInfo.getApplicationType())
            .selfIntroduce(entryInfo.getSelfIntroduce())
            .studyPlan(entryInfo.getStudyPlan())
            .educationalStatus(entryInfo.getEducationalStatus())
            .build();
		}

}