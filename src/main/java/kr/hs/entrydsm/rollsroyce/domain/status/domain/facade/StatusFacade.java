package kr.hs.entrydsm.rollsroyce.domain.status.domain.facade;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.StatusNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
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

    public List<User> findAllPassStatusTrue() {
        return statusRepository.findAllByIsFirstRoundPassTrue()
                .stream().map(Status::getUser)
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
