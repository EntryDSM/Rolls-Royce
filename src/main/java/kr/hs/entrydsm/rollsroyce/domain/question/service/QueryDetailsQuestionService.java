package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.AccessDeniedQuestionException;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryDetailsQuestionResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryDetailsQuestionService {
    private final UserFacade userFacade;
    private final AdminFacade adminFacade;
    private final ReplyRepository replyRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public QueryDetailsQuestionResponse execute(Long questionId) {
        Question question =
                questionRepository.findById(questionId).orElseThrow(() -> QuestionNotFoundException.EXCEPTION);

        if (adminFacade.getIsAdmin()) {
            return QueryDetailsQuestionResponse.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .username(question.getUserName())
                    .isReplied(question.getIsReplied())
                    .isPublic(question.getIsPublic())
                    .createdAt(question.getCreatedAt())
                    .reply(getReply(question))
                    .build();
        }

        if (!question.getIsPublic() && !userFacade.getCurrentUser().getId().equals(question.getUserId())) {
            throw AccessDeniedQuestionException.EXCEPTION;
        }

        return getQuestion(question);
    }

    private QueryDetailsQuestionResponse getQuestion(Question question) {
        return QueryDetailsQuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .username(question.getUserName())
                .isReplied(question.getIsReplied())
                .isMine(getIsMine(question.getUserId()))
                .isPublic(question.getIsPublic())
                .createdAt(question.getCreatedAt())
                .reply(getReply(question))
                .build();
    }

    private QueryDetailsQuestionResponse.ReplyDto getReply(Question question) {
        Reply reply = replyRepository.findByQuestion(question);

        if (reply == null) {
            return null;
        }

        return QueryDetailsQuestionResponse.ReplyDto.builder()
                .title(reply.getTitle())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }

    private Boolean getIsMine(Long userId) {
        User user = userFacade.getCurrentUser();
        return user.getId().equals(userId);
    }
}
