package kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QueryQuestionResponse {
    private final List<QuestionDto> questions;

    @Getter
    @Builder
    public static final class QuestionDto {
        private final Long id;
        private final String title;
        private final LocalDateTime createdAt;
        private final Boolean isReplied;
        private final String username;
        private final Boolean isPublic;
    }
}
