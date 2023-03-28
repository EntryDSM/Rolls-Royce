package kr.hs.entrydsm.rollsroyce.domain.status.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {
    List<Status> findAllByIsFirstRoundPassTrue();
}
