package kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
<<<<<<< main:src/main/java/kr/hs/entrydsm/rollsroyce/domain/qna/presentation/dto/response/QueryDetailsQnaResponse.java
public class QueryDetailsQnaResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
=======
public class QueryDetailsQuestionResponse {
    private final QuestionDto question;
    private final ReplyDto reply;

    @Getter
    @Builder
    public static class QuestionDto {
        private final Long id;
        private final String title;
        private final String content;
        private final String username;
        private final LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class ReplyDto {
        private final String title;
        private final String content;
        private final LocalDateTime createdAt;
    }
>>>>>>> ♻️ :: main rebase:src/main/java/kr/hs/entrydsm/rollsroyce/domain/question/presentation/dto/response/QueryDetailsQuestionResponse.java
}
