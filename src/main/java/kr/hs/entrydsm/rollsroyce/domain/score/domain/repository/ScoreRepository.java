package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {
}
