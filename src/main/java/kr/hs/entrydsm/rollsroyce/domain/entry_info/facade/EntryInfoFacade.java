package kr.hs.entrydsm.rollsroyce.domain.entry_info.facade;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.exception.EntryInfoNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return entryInfoRepository.findById(receiptCode)
                .orElseThrow(() -> EntryInfoNotFoundException.EXCEPTION);
    }
}
