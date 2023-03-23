package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import org.springframework.data.repository.CrudRepository;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.CheckPasswordLimit;

public interface CheckPasswordLimitRepository extends CrudRepository<CheckPasswordLimit, String> {
    boolean existsById(String id);
}
