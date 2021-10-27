package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryInformationService {

	private final UserFacade userFacade;

	public QueryInformationResponse execute() {
		return userFacade
				.getUserByCode(userFacade.getCurrentReceiptCode())
				.queryInformation();
	}

}
