package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.CreateNoticeRequest;

@RequiredArgsConstructor
@Service
public class CreateNoticeService {
    private final AdminFacade adminFacade;
    private final NoticeRepository postRepository;

    @Transactional
    public void execute(CreateNoticeRequest request) {
        Admin admin = adminFacade.getAdmin();

        postRepository.save(createPost(request, admin));
    }

    private Notice createPost(CreateNoticeRequest request, Admin admin) {
        return Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .isPinned(request.getIsPinned())
                .admin(admin)
                .build();
    }
}
