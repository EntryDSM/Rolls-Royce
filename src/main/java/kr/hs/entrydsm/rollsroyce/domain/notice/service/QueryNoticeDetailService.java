package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.exception.NoticeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeDetailResponse;

@RequiredArgsConstructor
@Service
public class QueryNoticeDetailService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public QueryNoticeDetailResponse execute(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        return QueryNoticeDetailResponse.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .created_at(notice.getCreatedAt())
                .type(notice.getType())
                .image(notice.getImage())
                .build();
    }
}
