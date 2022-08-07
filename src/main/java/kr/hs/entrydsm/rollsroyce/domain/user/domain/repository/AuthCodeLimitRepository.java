package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeLimitRepository extends CrudRepository<AuthCodeLimit, String> {
}
