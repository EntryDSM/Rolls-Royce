package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChangeIntroduceService {

	private final EntryInfoFacade entryInfoFacade;

	@Transactional
	public void execute(ChangeIntroduceRequest request) {
		EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
		entryInfo.updateSelfIntroduce(request.getContent());
	}

}
