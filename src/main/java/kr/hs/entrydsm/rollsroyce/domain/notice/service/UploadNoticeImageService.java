package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UploadNoticeImageService {
    private final S3Util s3Util;
    private final AdminFacade adminFacade;

    @Transactional
    public String execute(MultipartFile file) {
        Admin admin = adminFacade.getAdmin();
        String fileName = s3Util.upload(file, "post/");

        if (Role.CONFIRM_APPLICATION.equals(admin.getRole())) {
            throw AdminNotAccessibleException.EXCEPTION;
        }

        return s3Util.generateObjectUrl(fileName);
    }
}
