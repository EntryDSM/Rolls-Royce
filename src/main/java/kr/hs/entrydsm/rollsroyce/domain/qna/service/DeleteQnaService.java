package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.NotQnaWriterException;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteQnaService {
    private final UserFacade userFacade;
    private final QnaFacade qnaFacade;
    private final QnaRepository qnaRepository;

    @Transactional
    public void execute(Long qnaId) {
        User user = userFacade.getCurrentUser();
        Qna qna = qnaFacade.getQnaById(qnaId);

        if (!user.equals(qna.getUser())) {
            throw .EXCEPTION;
        }

        qnaRepository.delete(qna);
    }
}
