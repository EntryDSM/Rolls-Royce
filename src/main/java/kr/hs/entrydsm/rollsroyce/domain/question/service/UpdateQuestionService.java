package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionWriterMisMatchedException;
import kr.hs.entrydsm.rollsroyce.domain.question.facade.QuestionFacade;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.request.UpdateQuestionRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class UpdateQuestionService {
    private final UserFacade userFacade;
    private final QuestionFacade questionFacade;

    @Transactional
    public void execute(Long questionId, UpdateQuestionRequest request) {
        User user = userFacade.getCurrentUser();
        Question question = questionFacade.getQuestionById(questionId);

        if (!user.getId().equals(question.getUserId())) {
            throw QuestionWriterMisMatchedException.EXCEPTION;
        }

        question.updateFeed(request.getTitle(), request.getContent(), request.getIsPublic());
    }
}
