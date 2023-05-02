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
<<<<<<< main:src/main/java/kr/hs/entrydsm/rollsroyce/domain/qna/presentation/dto/response/QueryQnaResponse.java
    public static final class QnaDto {
        private final Long id;
=======
    public static final class QuestionDto {
>>>>>>> ♻️ :: qna -> question:src/main/java/kr/hs/entrydsm/rollsroyce/domain/question/presentation/dto/response/QueryQuestionResponse.java
        private final String title;
        private final LocalDateTime createdAt;
        private final Boolean isReplied;
        private final String username;
        private final Boolean isPublic;
    }
}
