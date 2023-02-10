package kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}
