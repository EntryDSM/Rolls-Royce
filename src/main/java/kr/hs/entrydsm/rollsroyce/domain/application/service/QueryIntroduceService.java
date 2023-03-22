package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryIntroduceResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryIntroduceService {

    private final UserFacade userFacade;

    public QueryIntroduceResponse execute() {
        return new QueryIntroduceResponse(userFacade.querySelfIntroduce());
    }
}
