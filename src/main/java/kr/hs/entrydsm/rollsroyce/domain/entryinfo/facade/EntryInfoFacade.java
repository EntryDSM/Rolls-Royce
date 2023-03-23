package kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.exception.EntryInfoNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Component
public class EntryInfoFacade {
    private final EntryInfoRepository entryInfoRepository;
    private final UserFacade userFacade;

    public Long getCurrentReceiptCode() {
        return getCurrentEntryInfo().getReceiptCode();
    }

    public EntryInfo getCurrentEntryInfo() {
        User user = userFacade.getCurrentUser();
        if (user.getEntryInfo() == null) {
            throw EntryInfoNotFoundException.EXCEPTION;
        }
        return user.getEntryInfo();
    }

    public EntryInfo getEntryInfoByCode(Long receiptCode) {
        return entryInfoRepository.findById(receiptCode).orElseThrow(() -> EntryInfoNotFoundException.EXCEPTION);
    }
}
