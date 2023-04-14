package kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QueryQnaResponse {
    private final List<QnaDto> qnaList;

    @Builder
    public static final class QnaDto {
        private final String title;
        private final LocalDateTime createdAt;
        private final Boolean isReplied;
        private final String username;
    }
}
