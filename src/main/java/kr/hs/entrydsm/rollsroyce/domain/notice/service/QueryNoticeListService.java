package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QueryNoticeListService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public QueryNoticeResponse execute() {
        List<QueryNoticeResponse.NoticeDto> notices = noticeRepository.findAll().stream()
                .map(notice -> QueryNoticeResponse.NoticeDto.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .isPinned(notice.getIsPinned())
                        .type(notice.getType())
                        .createdAt(notice.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return new QueryNoticeResponse(notices);
    }
}
