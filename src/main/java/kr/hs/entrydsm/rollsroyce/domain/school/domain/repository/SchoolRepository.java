package kr.hs.entrydsm.rollsroyce.domain.school.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends CrudRepository<School, String> {
}
