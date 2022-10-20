package kr.hs.entrydsm.rollsroyce.domain.status.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Long> {
	List<Status> findAllByIsFirstRoundPassTrue();
}
