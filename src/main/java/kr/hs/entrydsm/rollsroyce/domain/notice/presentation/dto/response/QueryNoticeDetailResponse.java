package kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;

@Getter
@Builder
public class QueryNoticeDetailResponse {
    private String title;
    private String content;
    private LocalDateTime created_at;
    private NoticeType type;
    private String image;
}
