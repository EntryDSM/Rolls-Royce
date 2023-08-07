package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryUserInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryUserInfoService {
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryUserInfoResponse execute() {
        User user = userFacade.getCurrentUser();
        return new QueryUserInfoResponse(user.getName(), user.getTelephoneNumber(), user.getIsStudent());
    }
}
