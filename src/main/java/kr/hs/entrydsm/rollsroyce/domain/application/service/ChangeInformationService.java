package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeInformationService {

	private final UserFacade userFacade;

	public void execute(ChangeInformationRequest request) {
		userFacade
				.changeInformation(request);
	}

}
