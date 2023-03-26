package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.NotModifyQnaException;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.UpdateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class UpdateQnaService {
    private final UserFacade userFacade;
    private final QnaFacade qnaFacade;

    @Transactional
    public void execute(Long qnaId, UpdateQnaRequest request) {

        User user = userFacade.getCurrentUser();
        Qna qna = qnaFacade.getQnaById(qnaId);

        if (!user.equals(qna.getUser())) {
            throw NotModifyQnaException.EXCEPTION;
        }

        qna.updateFeed(request.getTitle(), request.getContent(), request.getIsPublic());
    }
}
