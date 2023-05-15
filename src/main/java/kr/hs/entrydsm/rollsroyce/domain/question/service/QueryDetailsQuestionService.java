package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.AccessDeniedQuestionException;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.ReplyNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.question.facade.QuestionFacade;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryDetailsQuestionResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryDetailsQuestionService {
    private final QuestionFacade questionFacade;
    private final UserFacade userFacade;
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public QueryDetailsQuestionResponse execute(Long questionId) {
        Question question = questionFacade.getQuestionById(questionId);
        User user = userFacade.getCurrentUser();

        if (!question.getIsPublic() && !user.getId().equals(question.getUserId())) {
            throw AccessDeniedQuestionException.EXCEPTION;
        }

        return QueryDetailsQuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .username(question.getUser().getName())
                .isReplied(question.getIsReplied())
                .isMine(getIsMine(question.getUserId()))
                .createdAt(question.getCreatedAt())
                .reply(getReply(question))
                .build();
    }

    private QueryDetailsQuestionResponse.ReplyDto getReply(Question question) {
        Reply reply = replyRepository.findByQuestion(question).orElseThrow(() -> ReplyNotFoundException.EXCEPTION);

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
