package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.request.CreateQuestionRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class CreateQuestionService {
    private final UserFacade userFacade;
    private final QuestionRepository questionRepository;

    @Transactional
    public void execute(CreateQuestionRequest request) {
        User user = userFacade.getCurrentUser();

        questionRepository.save(createQuestion(request, user));
    }

    private Question createQuestion(CreateQuestionRequest request, User user) {
        return Question.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .isPublic(request.getIsPublic())
                .user(user)
                .build();
    }
}
