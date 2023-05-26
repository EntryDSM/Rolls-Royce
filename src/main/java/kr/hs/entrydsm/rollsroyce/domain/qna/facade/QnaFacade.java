package kr.hs.entrydsm.rollsroyce.domain.qna.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.QnaNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

@RequiredArgsConstructor
@Component
public class QnaFacade {
    private final QnaRepository qnaRepository;

    public Qna getQnaById(Long id) {
        return qnaRepository.findById(id).orElseThrow(() -> QnaNotFoundException.EXCEPTION);
    }

    public List<Qna> getQnaAllByUser(User user) {
        return qnaRepository.findAllByUser(user);
    }
}
