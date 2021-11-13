package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryTypeService {

	private final UserFacade userFacade;
	private final ApplicationFacade applicationFacade;

	public QueryTypeResponse execute() {
		User user = userFacade
				.getCurrentUser();

		Application application;

		if(user.getEducationalStatus().equals(EducationalStatus.QUALIFICATION_EXAM))
			application = applicationFacade.getQualification(user.getReceiptCode());
		else
			application = applicationFacade.getGraduation(user.getReceiptCode());

		return user.queryUserApplication(application);
	}

}
