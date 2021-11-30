package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.service.dto.UpdateUserInformationDto;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Sex;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeInformationService {

	private final UserFacade userFacade;

	@Transactional
	public void execute(ChangeInformationRequest request) {
		User user = userFacade.getCurrentUser();
		user.updateUserInformation(
				UpdateUserInformationDto.builder()
				.name(request.getName())
				.sex(EnumUtil.getEnum(Sex.class, request.getSex()))
				.birthday(request.getBirthday())
				.parentName(request.getParentName())
				.parentTel(request.getParentTel())
				.telephoneNumber(request.getTelephoneNumber())
				.address(request.getAddress())
				.postCode(request.getPostCode())
				.detailAddress(request.getDetailAddress())
				.build()
		);
	}

}
