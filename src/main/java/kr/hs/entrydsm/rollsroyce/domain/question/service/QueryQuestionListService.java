package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryQuestionListResponse;

@RequiredArgsConstructor
@Service
public class QueryQuestionListService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public QueryQuestionListResponse execute() {
        List<Question> questions = questionRepository.findAllByOrderByIdDesc();

        return QueryQuestionListResponse.builder()
                .questions(questions.stream()
                        .map(question -> QueryQuestionListResponse.QuestionDto.builder()
                                .id(question.getId())
                                .title(question.getTitle())
                                .username(question.getUserName())
                                .isReplied(question.getIsReplied())
                                .isPublic(question.getIsPublic())
                                .createdAt(question.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
