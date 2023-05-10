package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.facade.QuestionFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.WriterMisMatchedException;

@RequiredArgsConstructor
@Service
public class DeleteQuestionService {
    private final UserFacade userFacade;
    private final QuestionFacade questionFacade;
    private final QuestionRepository questionRepository;

    @Transactional
    public void execute(Long questionId) {
        User user = userFacade.getCurrentUser();
        Question question = questionFacade.getQuestionById(questionId);

        if (!user.getId().equals(question.getUserId())) {
            throw WriterMisMatchedException.EXCEPTION;
        }

        questionRepository.delete(question);
    }
}
