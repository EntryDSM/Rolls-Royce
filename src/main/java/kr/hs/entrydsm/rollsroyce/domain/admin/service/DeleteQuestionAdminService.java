package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionNotFoundException;

@RequiredArgsConstructor
@Service
public class DeleteQuestionAdminService {
    private final QuestionRepository questionRepository;

    @Transactional
    public void execute(Long questionId) {
        Question question =
                questionRepository.findById(questionId).orElseThrow(() -> QuestionNotFoundException.EXCEPTION);
        ;

        questionRepository.delete(question);
    }
}
