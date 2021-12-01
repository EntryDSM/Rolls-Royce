package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryInformationService {

	private final UserFacade userFacade;
	private final S3Util s3Util;

	public QueryInformationResponse execute() {
		QueryInformationResponse response = userFacade
				.getCurrentUser()
				.queryInformation();
		response.setPhotoFileName(s3Util.generateObjectUrl(response.getPhotoFileName()));
		return response;
	}

}
