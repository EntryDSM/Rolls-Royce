package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QueryQuestionDetailAdminResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final Boolean isReplied;
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
