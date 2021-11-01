package kr.hs.entrydsm.rollsroyce.domain.application.service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeInformationService {

	private final UserFacade userFacade;

	@Transactional
	public void execute(ChangeInformationRequest request) {
		User user = userFacade.getCurrentUser();
		user.updateUserInformation(
				request.getName(), request.getSex(), request.getBirthday(),
				request.getParentName(), request.getParentTel(), request.getTelephoneNumber(), request.getHomeTel(),
				request.getAddress(), request.getPostCode(), request.getDetailAddress()
		);
	}

}
