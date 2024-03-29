package kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;

@Getter
@AllArgsConstructor
public class QueryNoticeResponse {
    private final List<NoticeDto> notices;

    @Getter
    @Builder
    public static final class NoticeDto {
        private final Long id;
        private final String title;
        private final Boolean isPinned;
        private final LocalDateTime createdAt;
        private final NoticeType type;
    }
}
