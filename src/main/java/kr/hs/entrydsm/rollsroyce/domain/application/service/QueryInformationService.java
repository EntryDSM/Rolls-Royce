package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class QueryInformationService {

    private final S3Util s3Util;

    private final UserFacade userFacade;

    public QueryInformationResponse execute() {
        QueryInformationResponse response = userFacade.queryInformation();

        response.setPhotoFileName(s3Util.generateObjectUrl(response.getPhotoFileName()));

        return response;
    }
}
