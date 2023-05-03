package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.UpdateNoticeRequest;

@RequiredArgsConstructor
@Service
public class UpdateNoticeService {
    private final NoticeRepository noticeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void execute(Long noticeId, UpdateNoticeRequest request) {
        Admin admin = adminFacade.getAdmin();
        Notice notice = noticeRepository.getById(noticeId);

        notice.updateNotice(request.getTitle(), request.getContent(), request.getType(), request.getIsPinned(), admin);
    }
}
