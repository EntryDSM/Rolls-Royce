package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {
	@Query("select a from tbl_calculated_score a "
			+ "join fetch a.user u "
			+ "where u.receiptCode = a.receiptCode and "
			+ "u.applicationType = :applicationType and "
			+ "u.isDaejeon = :isDaejeon order by a.totalScore desc")
	List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
}
