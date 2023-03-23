package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryIntroduceResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;

@RequiredArgsConstructor
@Service
public class QueryIntroduceService {

    private final EntryInfoFacade entryInfoFacade;

    public QueryIntroduceResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        return new QueryIntroduceResponse(entryInfo.getSelfIntroduce());
    }
}
