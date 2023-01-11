package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryIntroduceResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryIntroduceService {

    private final EntryInfoFacade entryInfoFacade;

    public QueryIntroduceResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        return new QueryIntroduceResponse(
                entryInfo.getSelfIntroduce()
        );
    }

}
