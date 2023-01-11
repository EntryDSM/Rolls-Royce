package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
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
