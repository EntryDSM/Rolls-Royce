package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.ApplicationCount;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

import org.springframework.data.repository.CrudRepository;

public interface ApplicationCountRepository extends CrudRepository<ApplicationCount, Long> {
	Optional<ApplicationCount> findByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
}
