package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.exception.RequestFailToTmapServerException;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.service.dto.UpdateUserInformationDto;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.client.TmapApi;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request.RouteRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.CoordinateResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChangeInformationService {

    private final EntryInfoFacade entryInfoFacade;
    private final TmapApi tmapApi;

    @Value("${tmap.app.key}")
    private String appKey;

    @Transactional
    public void execute(ChangeInformationRequest request) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        CoordinateResponse coordinate =
                tmapApi.getCoordinate(appKey, request.getAddress());
        RouteResponse distance = tmapApi.routeGuidance(appKey,
                RouteRequest.builder()
                        .startX(Double.parseDouble(coordinate.getLon()))
                        .startY(Double.parseDouble(coordinate.getLat()))
                        .totalValue(2)
                        .build()
        );
        if (distance.getFeatures().isEmpty())
            throw RequestFailToTmapServerException.EXCEPTION;

        entryInfo.updateEntryInformation(
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
                        .distance(distance.getTotalDistance())
                        .build()
        );
    }

}
