package kr.hs.entrydsm.rollsroyce.domain.application.service;

import java.util.Objects;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.ProcessNotCompletedException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalSubmitService {

	private final UserFacade userFacade;

	@Transactional
	public void execute() {
		User user = userFacade.getCurrentUser();

		if(user.hasEmptyInfo() || checkApplication(user))
			throw ProcessNotCompletedException.EXCEPTION;

		user.isSubmitToTrue();
	}

	private boolean checkApplication(User user) {
		Application application =
				Objects.requireNonNullElse(user.getGraduation(), user.getQualification());
		return application.hasEmptyInfo();
	}

}
