package kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response;

import lombok.AllArgsConstructor;
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
    @AllArgsConstructor
    public static class QnaDto {
        private final String title;
        private final String content;
        private final String username;
        private final LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReplyDto {
        private final String title;
        private final String content;
        private final LocalDateTime createdAt;
    }
}
