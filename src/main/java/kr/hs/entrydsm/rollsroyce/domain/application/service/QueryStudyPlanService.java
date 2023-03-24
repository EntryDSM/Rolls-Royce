package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryStudyPlanResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;

@RequiredArgsConstructor
@Service
public class QueryStudyPlanService {

    private final EntryInfoFacade entryInfoFacade;

    public QueryStudyPlanResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        return new QueryStudyPlanResponse(entryInfo.getStudyPlan());
    }
}
