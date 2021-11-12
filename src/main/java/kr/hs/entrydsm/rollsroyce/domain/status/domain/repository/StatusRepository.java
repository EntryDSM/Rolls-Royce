package kr.hs.entrydsm.rollsroyce.domain.status.domain.repository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long> {
	List<Status> findAllByIsFirstRoundPassTrue();
}
