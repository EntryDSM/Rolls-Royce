package kr.hs.entrydsm.rollsroyce.domain.qna.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.QnaNotFoundException;

@RequiredArgsConstructor
@Component
public class QnaFacade {
    private final QnaRepository qnaRepository;

    public Qna getQnaById(Long id) {
        return qnaRepository.findById(id).orElseThrow(() -> QnaNotFoundException.EXCEPTION);
    }
}
