package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.CheckPasswordLimit;
import org.springframework.data.repository.CrudRepository;

public interface CheckPasswordLimitRepository extends CrudRepository<CheckPasswordLimit, String> {
    boolean existsById(String id);
}
