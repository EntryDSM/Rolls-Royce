package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class QueryInformationService {

    private final S3Util s3Util;
    private final EntryInfoFacade entryInfoFacade;

    public QueryInformationResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        QueryInformationResponse response = queryInformation(entryInfo);

        response.setPhotoFileName(s3Util.generateObjectUrl(response.getPhotoFileName()));

        return response;
    }

    private QueryInformationResponse queryInformation(EntryInfo entryInfo) {
        return QueryInformationResponse.builder()
                .address(entryInfo.getAddress())
                .parentTel(entryInfo.getParentTel())
                .sex(String.valueOf(entryInfo.getSex()))
                .telephoneNumber(entryInfo.getUserTelephoneNumber())
                .name(entryInfo.getUserName())
                .detailAddress(entryInfo.getDetailAddress())
                .parentName(entryInfo.getParentName())
                .postCode(entryInfo.getPostCode())
                .photoFileName(entryInfo.getPhotoFileName())
                .birthday(String.valueOf(entryInfo.getBirthday()))
                .build();
    }
}
