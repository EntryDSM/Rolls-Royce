package kr.hs.entrydsm.rollsroyce.domain.status.domain.facade;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.StatusNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class StatusFacade {

    private final StatusRepository statusRepository;

    public Status getStatusByReceiptCode(long receiptCode) {
        return statusRepository.findById(receiptCode)
                .orElseThrow(() -> StatusNotFoundException.EXCEPTION);
    }

    public List<EntryInfo> findAllPassStatusTrue() {
        return statusRepository.findAllByFirstRoundPassIsTrue()
                .stream()
                .map(Status::getEntryInfo)
                .collect(Collectors.toList());
    }

    public void saveStatus(Status status) {
        statusRepository.save(status);
    }

    public void updateIsFirstRoundPass(List<Long> receiptCodes) {
        receiptCodes.forEach(receiptCode ->
                saveStatus(
                        getStatusByReceiptCode(receiptCode)
                                .updateIsFirstRoundPass()
                )
        );
    }

}
