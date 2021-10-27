package kr.hs.entrydsm.rollsroyce.domain.application.service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChangeIntroduceService {

    private final UserFacade userFacade;

    @Transactional
    public void execute(ChangeIntroduceRequest request) {
		User user = userFacade.getUserByCode(
				userFacade.getCurrentReceiptCode()
		);
		user.updateSelfIntroduce(request.getContent());
    }
}
