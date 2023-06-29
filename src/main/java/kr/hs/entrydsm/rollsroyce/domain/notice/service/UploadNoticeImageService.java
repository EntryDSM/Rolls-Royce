package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UploadNoticeImageService {

    private final S3Util s3Util;
    private final AdminFacade adminFacade;

    @Transactional
    public String execute(MultipartFile file) {
        String filename;

        if (file != null) {
            filename = s3Util.putObject(file, "/post");
        } else {
            filename = s3Util.upload(file, "/post");
        }

        return s3Util.generateObjectUrl(filename);
    }
}
