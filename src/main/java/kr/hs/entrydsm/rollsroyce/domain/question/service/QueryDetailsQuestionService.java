package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.AccessDeniedQuestionException;
import kr.hs.entrydsm.rollsroyce.domain.question.facade.QuestionFacade;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryDetailsQuestionResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryDetailsQuestionService {
    private final QuestionFacade questionFacade;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryDetailsQuestionResponse execute(Long questionId) {
        Question question = questionFacade.getQuestionById(questionId);
        User user = userFacade.getCurrentUser();

        if (!question.getIsPublic() && !user.getId().equals(question.getId())) {
            throw AccessDeniedQuestionException.EXCEPTION;
        }
        return getQuestion(questionId);
    }

    private QueryDetailsQuestionResponse getQuestion(Long questionId) {
        Question question = questionFacade.getQuestionById(questionId);

        return QueryDetailsQuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .isReplied(question.getIsReplied())
                .username(question.getUserName())
                .createdAt(question.getCreatedAt())
                .build();
    }
}
