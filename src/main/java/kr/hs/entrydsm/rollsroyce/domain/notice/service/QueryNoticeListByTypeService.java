package kr.hs.entrydsm.rollsroyce.domain.notice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository.NoticeRepository;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeResponse;

@RequiredArgsConstructor
@Service
public class QueryNoticeListByTypeService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public QueryNoticeResponse execute(NoticeType type) {
        List<QueryNoticeResponse.NoticeDto> notices = getNoticeList(type).stream()
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

    private List<Notice> getNoticeList(NoticeType type) {
        if (type == null) {
            return noticeRepository.findAll();
        } else {
            return noticeRepository.findAllByType(type);
        }
    }
}
