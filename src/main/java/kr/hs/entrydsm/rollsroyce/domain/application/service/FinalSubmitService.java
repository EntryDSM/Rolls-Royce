package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.ProcessNotCompletedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinalSubmitService {

	private final UserFacade userFacade;
	private final StatusFacade statusFacade;
	private final ApplicationFacade applicationFacade;

	@Transactional
	public void execute() {
		User user = userFacade.getCurrentUser();

		if(user.hasEmptyInfo() || checkApplication(user))
			throw ProcessNotCompletedException.EXCEPTION;

		statusFacade.getStatusByReceiptCode(user.getReceiptCode())
				.isSubmitToTrue();
	}

	private boolean checkApplication(User user) {
		Application application;

		if(user.getEducationalStatus().equals(EducationalStatus.QUALIFICATION_EXAM))
			application = applicationFacade.getQualification(user.getReceiptCode());
		else application = applicationFacade.getGraduation(user.getReceiptCode());

		return application.hasEmptyInfo();
	}

}
