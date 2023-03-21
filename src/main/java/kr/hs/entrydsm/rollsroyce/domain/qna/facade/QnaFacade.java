package kr.hs.entrydsm.rollsroyce.domain.qna.facade;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.QnaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QnaFacade {
    private final QnaRepository qnaRepository;

    public Qna getQnaById(Long id) {
        return qnaRepository.findById(id)
                .orElseThrow(() -> QnaNotFoundException.EXCEPTION);
    }
}
