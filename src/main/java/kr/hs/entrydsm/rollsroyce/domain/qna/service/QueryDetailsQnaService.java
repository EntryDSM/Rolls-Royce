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

        return QueryDetailsQnaResponse.builder().qna(getQna(qna.getId())).build();
    }

    private QueryDetailsQnaResponse.QnaDto getQna(Long qnaId) {
        Qna qna = qnaFacade.getQnaById(qnaId);

        return QueryDetailsQnaResponse.QnaDto.builder()
                .title(qna.getTitle())
                .content(qna.getContent())
                .createdAt(qna.getCreatedAt())
                .username(qna.getUser().getName())
                .build();
    }

    private QueryDetailsQnaResponse.ReplyDto getReply(Long replyId) {
        Reply reply = replyRepository.getById(replyId);

        return QueryDetailsQnaResponse.ReplyDto.builder()
                .title(reply.getTitle())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
