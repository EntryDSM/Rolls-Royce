package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;

@RequiredArgsConstructor
@Service
public class ChangeIntroduceService {

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute(ChangeIntroduceRequest request) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        entryInfo.updateSelfIntroduce(request.getContent());
    }
}
