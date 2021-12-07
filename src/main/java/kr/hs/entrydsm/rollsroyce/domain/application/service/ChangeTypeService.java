package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
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

	private final UserFacade userFacade;
	private final GraduationRepository graduationRepository;
	private final QualificationRepository qualificationRepository;

	@Transactional
	public void execute(ChangeTypeRequest request) {
		User user = userFacade.getCurrentUser();

		if(request.getEducationalStatus().equals(EducationalStatus.QUALIFICATION_EXAM.name())) {
			graduationRepository.deleteById(user.getReceiptCode());
			qualificationRepository.save(
					new Qualification(user, request.getGraduatedAt())
			);
		} else {
			qualificationRepository.deleteById(user.getReceiptCode());
			graduationRepository.save(
					new Graduation(user, request.getGraduatedAt(),
							EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus()))
			);
		}

		user.updateUserApplication(
				EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus()),
				EnumUtil.getEnum(ApplicationType.class, request.getApplicationType()),
				request.getIsDaejeon(), EnumUtil.getEnum(ApplicationRemark.class, request.getApplicationRemark()),
				EnumUtil.getEnum(HeadCount.class, request.getHeadcount()));
	}

}
