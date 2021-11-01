package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QualificationCaseRepository extends CrudRepository<QualificationCase, Long> {
    Optional<QualificationCase> findByReceiptCode(long receiptCode);
}
