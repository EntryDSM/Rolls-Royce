package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.EducationalStatusNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryTypeService {

    private final ApplicationFacade applicationFacade;

    private final EntryInfoFacade entryInfoFacade;

    public QueryTypeResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        if (entryInfo.getEducationalStatus() == null)
            throw EducationalStatusNullException.EXCEPTION;

        Application application = applicationFacade
                .getApplication(entryInfo.getReceiptCode(), entryInfo.getEducationalStatus());

        return entryInfo.queryUserApplication(application);
    }

}
