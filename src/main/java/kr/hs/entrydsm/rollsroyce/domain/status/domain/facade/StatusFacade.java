package kr.hs.entrydsm.rollsroyce.domain.status.domain.facade;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.StatusNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusFacade {

	private final UserFacade userFacade;
	private final StatusRepository statusRepository;

	public Status getStatusByReceiptCode(long receiptCode) {
		return statusRepository.findById(receiptCode)
				.orElseThrow(() -> StatusNotFoundException.EXCEPTION);
	}

	public boolean getCurrentIsSubmitted() {
		return getStatusByReceiptCode(userFacade.getCurrentReceiptCode())
				.getIsSubmitted();
	}

}
