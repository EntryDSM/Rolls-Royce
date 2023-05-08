package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeResponse;

@RequiredArgsConstructor
@Service
public class QueryNoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public QueryNoticeResponse execute(String type) {
        List<Notice> notices = noticeRepository.findAllByType(type);

        return QueryNoticeResponse.builder()
                .notices(notices.stream()
                        .map(notice -> QueryNoticeResponse.NoticeDto.builder()
                                .id(notice.getId())
                                .title(notice.getTitle())
                                .isPinned(notice.getIsPinned())
                                .createdAt(notice.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
