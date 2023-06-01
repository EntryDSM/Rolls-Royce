package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.request.UpdateQuestionRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.WriterMisMatchedException;

@RequiredArgsConstructor
@Service
public class UpdateQuestionService {
    private final UserFacade userFacade;
    private final QuestionRepository questionRepository;

    @Transactional
    public void execute(Long questionId, UpdateQuestionRequest request) {
        User user = userFacade.getCurrentUser();
        Question question =
                questionRepository.findById(questionId).orElseThrow(() -> QuestionNotFoundException.EXCEPTION);

        if (!user.getId().equals(question.getUserId())) {
            throw WriterMisMatchedException.EXCEPTION;
        }

        question.updateFeed(request.getTitle(), request.getContent(), request.getIsPublic());
    }
}
