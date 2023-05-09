package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public QueryDetailsQnaResponse execute(Long questionId) {
        Qna qna = qnaFacade.getQnaById(questionId);
        User user = userFacade.getCurrentUser();

        if (!qna.getIsPublic() && !user.getId().equals(qna.getId())) {
            throw AccessDeniedQnaException.EXCEPTION;
        }

        return getQna(questionId);
    }

    private QueryDetailsQnaResponse getQna(Long qnaId) {
        Qna qna = qnaFacade.getQnaById(qnaId);

        return QueryDetailsQnaResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .username(qna.getUser().getName())
                .isReplied(qna.getIsReplied())
                .isMine(getIsMine(qna.getUserId()))
                .createdAt(qna.getCreatedAt())
                .build();
    }

    private Boolean getIsMine(Long userId) {
        User user = userFacade.getCurrentUser();
        if (user.getId().equals(userId)) {
            return true;
        }
        return false;
    }
}
