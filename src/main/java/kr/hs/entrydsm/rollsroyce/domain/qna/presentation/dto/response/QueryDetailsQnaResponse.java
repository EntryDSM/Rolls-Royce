package kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QueryDetailsQnaResponse {
    private final QnaDto qna;
    private final ReplyDto reply;

    @Getter
    @Builder
    public static final class QnaDto {
        private final Long id;
        private final String title;
        private final String content;
        private final String username;
        private final Boolean isReplied;
        private final LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static final class ReplyDto {
        private final Long id;
        private final String title;
        private final String content;
        private final LocalDateTime createdAt;
    }
}
