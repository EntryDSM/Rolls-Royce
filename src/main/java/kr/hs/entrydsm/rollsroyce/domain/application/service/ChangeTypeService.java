package kr.hs.entrydsm.rollsroyce.domain.application.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeTypeService {

	private final QualificationRepository qualificationRepositroy;
	private final GraduationRepository graduationRepository;
	private final UserFacade userFacade;

	public void execute(ChangeTypeRequest request) {
		User user = userFacade.getUserByCode(
				userFacade.getCurrentReceiptCode()
		);
		user.updateUserApplication(
				EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus()),
				EnumUtil.getEnum(ApplicationType.class, request.getApplicationType()),
				request.getIsDaejeon(), EnumUtil.getEnum(ApplicationRemark.class, request.getApplicationRemark()),
				EnumUtil.getEnum(HeadCount.class, request.getHeadcount()));
		deleteLegacyApplication(request, user);
	}

	private void deleteLegacyApplication(ChangeTypeRequest request, User user) {
		if(!user.hasApplication())
			return;

		if(request.getEducationalStatus().equals(EducationalStatus.QUALIFICATION_EXAM.name()))
			graduationRepository.delete(user.getGraduation());
		else
			qualificationRepositroy.delete(user.getQualification());
	}

}
