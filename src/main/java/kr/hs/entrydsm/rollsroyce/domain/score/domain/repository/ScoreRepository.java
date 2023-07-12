package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long>, ScoreCustomRepository {
    Score findByReceiptCode(Long receiptCode);
}
