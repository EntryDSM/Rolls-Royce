package kr.hs.entrydsm.rollsroyce.domain.screen.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.Screen;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.repository.ScreenRepository;
import kr.hs.entrydsm.rollsroyce.domain.screen.exception.ScreenNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.request.UpdateScreenRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UpdateScreenService {
    private final ScreenRepository screenRepository;
    private final AdminFacade adminFacade;
    private final S3Util s3Util;

    @Transactional
    public void execute(Long screenId, UpdateScreenRequest request) {
        String fileName = s3Util.upload(request.getImage(), "screen/");
        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> ScreenNotFoundException.EXCEPTION);

        screen.updateScreen(fileName);
    }
}
