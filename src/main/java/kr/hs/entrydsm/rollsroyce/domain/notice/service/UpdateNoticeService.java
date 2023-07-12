package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.exception.NoticeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UpdateNoticeService {
    private final NoticeRepository noticeRepository;
    private final AdminFacade adminFacade;
    private final S3Util s3Util;

    @Transactional
    public String execute(Long noticeId, UpdateNoticeRequest request) {
        Admin admin = adminFacade.getAdmin();
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> NoticeNotFoundException.EXCEPTION);
        String fileName;

        if (notice.getImage() == null) {
            fileName = s3Util.upload(request.getFile(), "/post");
        } else {
            fileName = s3Util.putObject(request.getFile(), "/post");
        }

        notice.updateNotice(
                request.getTitle(), request.getContent(), request.getType(), request.getIsPinned(), admin, fileName);

        return s3Util.generateObjectUrl(fileName);
    }
}
