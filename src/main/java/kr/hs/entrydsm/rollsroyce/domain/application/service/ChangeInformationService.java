package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.service.dto.UpdateUserInformationDto;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Sex;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.client.TmapApi;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request.RouteRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.CoordinateResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeInformationService {

    private final UserFacade userFacade;
    private final TmapApi tmapApi;

	@Value("${tmap.app.key}")
	private String appKey;

    @Transactional
    public void execute(ChangeInformationRequest request) {
        User user = userFacade.getCurrentUser();

		CoordinateResponse coordinate =
				tmapApi.getCoordinate(appKey, request.getAddress());
		RouteResponse distance = tmapApi.routeGuidance(appKey,
				RouteRequest.builder()
						.startX(Double.parseDouble(coordinate.getLon()))
						.startY(Double.parseDouble(coordinate.getLat()))
						.totalValue(2)
						.build()
		);
		if(distance.getFeatures().isEmpty())
			throw RequestFailToOtherServerException.EXCEPTION;

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
						.distance(distance.getTotalDistance())
                        .build()
        );
    }

}
