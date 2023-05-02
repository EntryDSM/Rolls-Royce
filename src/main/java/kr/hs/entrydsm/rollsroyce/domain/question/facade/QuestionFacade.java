package kr.hs.entrydsm.rollsroyce.domain.question.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionNotFoundException;

@RequiredArgsConstructor
@Component
public class QuestionFacade {
    private final QuestionRepository questionRepository;

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> QuestionNotFoundException.EXCEPTION);
    }
}
