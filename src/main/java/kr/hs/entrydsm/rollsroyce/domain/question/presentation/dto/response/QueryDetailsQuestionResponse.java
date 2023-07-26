package kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QueryDetailsQuestionResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final Boolean isReplied;
    private final Boolean isMine;
    private final Boolean isPublic;
    private final LocalDateTime createdAt;
    private final ReplyDto reply;

    @Getter
    @Builder
    public static final class ReplyDto {
        private final String title;
        private final String content;
        private final LocalDateTime createdAt;
    }
}
