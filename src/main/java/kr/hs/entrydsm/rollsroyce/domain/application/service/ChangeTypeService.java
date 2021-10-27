package kr.hs.entrydsm.rollsroyce.domain.application.service;

import javax.transaction.Transactional;

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

@Service
@RequiredArgsConstructor
public class ChangeTypeService {

	private final UserFacade userFacade;

	@Transactional
	public void execute(ChangeTypeRequest request) {
		User user = userFacade.getUserByCode(
				userFacade.getCurrentReceiptCode()
		);
		user.updateUserApplication(
				EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus()),
				EnumUtil.getEnum(ApplicationType.class, request.getApplicationType()),
				request.getIsDaejeon(), EnumUtil.getEnum(ApplicationRemark.class, request.getApplicationRemark()),
				EnumUtil.getEnum(HeadCount.class, request.getHeadcount()));
	}

}
