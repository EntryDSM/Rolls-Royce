package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.AccessDeniedQnaException;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response.QueryDetailsQnaResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryDetailsQnaService {
    private final QnaFacade qnaFacade;
    private final UserFacade userFacade;
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public QueryDetailsQnaResponse execute(Long questionId) {
        Qna qna = qnaFacade.getQnaById(questionId);
        User user = userFacade.getCurrentUser();

        if (!qna.getIsPublic() && !user.getId().equals(qna.getId())) {
            throw AccessDeniedQnaException.EXCEPTION;
        }

        return getQuestionDetails(qna.getId());
    }

    private QueryDetailsQnaResponse getQuestionDetails(Long qnaId) {
        Qna qna = qnaFacade.getQnaById(qnaId);

        return QueryDetailsQnaResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .username(qna.getUser().getName())
                .isReplied(qna.getIsReplied())
                .createdAt(qna.getCreatedAt())
                .reply(getReply(qnaId))
                .build();
    }

    private QueryDetailsQnaResponse.ReplyDto getReply(Long qnaId) {
        Reply reply = replyRepository.findByQnaId(qnaId);

        if (reply == null) {
            return null;
        }

        return QueryDetailsQnaResponse.ReplyDto.builder()
                .title(reply.getTitle())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
