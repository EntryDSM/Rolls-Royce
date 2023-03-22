package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class ChangeIntroduceService {

    private final UserFacade userFacade;

    @Transactional
    public void execute(ChangeIntroduceRequest request) {
        User user = userFacade.getCurrentUser();
        user.updateSelfIntroduce(request.getContent());
    }
}
