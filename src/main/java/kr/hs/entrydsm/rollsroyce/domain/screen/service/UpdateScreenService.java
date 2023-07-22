package kr.hs.entrydsm.rollsroyce.domain.screen.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.screen.domain.Screen;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.repository.ScreenRepository;
import kr.hs.entrydsm.rollsroyce.domain.screen.exception.ScreenNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UpdateScreenService {
    private final ScreenRepository screenRepository;
    private final S3Util s3Util;

    @Transactional
    public void execute(Long screenId, MultipartFile file) {
        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> ScreenNotFoundException.EXCEPTION);
        String fileName = s3Util.putObject(file, "screen/");
        screen.updateScreen(fileName);
    }
}
