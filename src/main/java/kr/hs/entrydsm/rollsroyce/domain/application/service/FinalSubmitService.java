package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.ProcessNotCompletedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.AlreadySubmitException;
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

		Status status = statusFacade.getStatusByReceiptCode(user.getReceiptCode());

		if(Boolean.TRUE.equals(status.getIsSubmitted()))
			throw AlreadySubmitException.EXCEPTION;

		status.isSubmitToTrue();
	}

	private boolean checkApplication(User user) {
		Application application;

		if(EducationalStatus.QUALIFICATION_EXAM.equals(user.getEducationalStatus()))
			application = applicationFacade.getQualification(user.getReceiptCode());
		else application = applicationFacade.getGraduation(user.getReceiptCode());

		return application.hasEmptyInfo();
	}

}
