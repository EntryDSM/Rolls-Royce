package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.ApplicationCount;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.ApplicationType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationCountRepository extends CrudRepository<ApplicationCount, Long> {
    Optional<ApplicationCount> findByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);

    List<ApplicationCount> findAll();
}
