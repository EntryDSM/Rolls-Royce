package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;

@RequiredArgsConstructor
@Service
public class DeleteQnaService {
    private final QnaFacade qnaFacade;
    private final QnaRepository qnaRepository;

    @Transactional
    public void execute(Long qnaId) {
        Qna qna = qnaFacade.getQnaById(qnaId);

        qnaRepository.delete(qna);
    }
}
