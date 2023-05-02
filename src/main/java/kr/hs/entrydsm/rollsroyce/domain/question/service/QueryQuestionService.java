package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryQuestionResponse;

@RequiredArgsConstructor
@Service
public class QueryQuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public QueryQuestionResponse execute() {

        return getQuestions();
    }

    private QueryQuestionResponse getQuestions() {
        List<Question> questions = questionRepository.findAllByOrderByIdDesc();

        return QueryQuestionResponse.builder()
                .questions(questions.stream()
                        .map(question -> QueryQuestionResponse.QuestionDto.builder()
                                .title(question.getTitle())
                                .username(question.getUserName())
                                .isReplied(question.getIsReplied())
                                .createdAt(question.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
