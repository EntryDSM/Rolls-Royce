package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryTypeService {

	private final UserFacade userFacade;

	public QueryTypeResponse execute() {
		return userFacade
				.getUserByCode(userFacade.getCurrentReceiptCode())
				.queryUserApplication();
	}

}
