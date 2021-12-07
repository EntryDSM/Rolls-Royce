package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.HeadCount;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeTypeService {

	private final ApplicationFacade applicationFacade;

	private final UserFacade userFacade;

	@Transactional
	public void execute(ChangeTypeRequest request) {
		User user = userFacade.getCurrentUser();

		if (EducationalStatus.QUALIFICATION_EXAM.name().equals(request.getEducationalStatus())) {
			applicationFacade.updateQualification(user, request.getGraduatedAt());
		}
		else {
			applicationFacade.updateGraduation(user, request.getGraduatedAt(), request.getEducationalStatus());
		}

		user.updateUserApplication(
				EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus()),
				EnumUtil.getEnum(ApplicationType.class, request.getApplicationType()),
				request.getIsDaejeon(), EnumUtil.getEnum(ApplicationRemark.class, request.getApplicationRemark()),
				EnumUtil.getEnum(HeadCount.class, request.getHeadcount()));
	}

}
