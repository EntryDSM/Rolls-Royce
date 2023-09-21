package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.CreateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class CreateNoticeService {
    private final AdminFacade adminFacade;
    private final NoticeRepository noticeRepository;
    private final S3Util s3Util;

    @Transactional
    public void execute(CreateNoticeRequest request) {
        Admin admin = adminFacade.getAdmin();

        if (Role.CONFIRM_APPLICATION.equals(admin.getRole())) {
            throw AdminNotAccessibleException.EXCEPTION;
        }

        noticeRepository.save(createPost(request, admin));
    }

    private Notice createPost(CreateNoticeRequest request, Admin admin) {
        return Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .isPinned(request.getIsPinned())
                .image(request.getFileName())
                .admin(admin)
                .build();
    }
}
