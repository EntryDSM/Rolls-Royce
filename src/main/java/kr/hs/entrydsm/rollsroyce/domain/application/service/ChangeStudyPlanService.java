package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeStudyPlanRequest;
import kr.hs.entrydsm.rollsroyce.domain.entryInfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryInfo.facade.EntryInfoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChangeStudyPlanService {

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute(ChangeStudyPlanRequest request) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        entryInfo.updateStudyPlan(request.getContent());
    }

}
