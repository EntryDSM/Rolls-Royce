package kr.hs.entrydsm.rollsroyce.domain.entryinfo.service;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CancelEntryService {
    private final EntryInfoFacade entryInfoFacade;
    private final EntryInfoRepository entryInfoRepository;

    @Transactional
    public void execute(Long code) {
        EntryInfo entryInfo = entryInfoFacade.getEntryInfoByCode(code);
        entryInfoRepository.delete(entryInfo);
    }
}
