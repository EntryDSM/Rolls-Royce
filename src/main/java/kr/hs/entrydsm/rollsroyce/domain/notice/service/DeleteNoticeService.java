package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.exception.NoticeNotFoundException;

@RequiredArgsConstructor
@Service
public class DeleteNoticeService {
    private NoticeRepository noticeRepository;

    @Transactional
    public void execute(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> NoticeNotFoundException.EXCEPTION);
        noticeRepository.delete(notice);
    }
}
