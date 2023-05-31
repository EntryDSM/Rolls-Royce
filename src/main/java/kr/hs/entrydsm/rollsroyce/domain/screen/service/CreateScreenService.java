package kr.hs.entrydsm.rollsroyce.domain.screen.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.Screen;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.repository.ScreenRepository;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class CreateScreenService {
    private final ScreenRepository screenRepository;
    private final AdminFacade adminFacade;
    private final S3Util s3Util;

    @Transactional
    public String execute(MultipartFile file) {
        Admin admin = adminFacade.getAdmin();

        String fileName = s3Util.upload(file, "screen/");

        screenRepository.save(Screen.builder().image(fileName).admin(admin).build());

        return s3Util.generateObjectUrl(fileName);
    }
}
