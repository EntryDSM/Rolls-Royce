package kr.hs.entrydsm.rollsroyce.domain.entryinfo.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.presentation.dto.response.StatusResponse;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;

@RequiredArgsConstructor
@Service
public class EntryStatusService {

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    public StatusResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        Status status = statusFacade.getStatusByReceiptCode(entryInfo.getReceiptCode());

        return StatusResponse.builder()
                .receiptCode(entryInfo.getReceiptCode())
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
