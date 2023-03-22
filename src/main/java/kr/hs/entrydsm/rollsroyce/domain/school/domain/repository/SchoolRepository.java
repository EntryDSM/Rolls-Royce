package kr.hs.entrydsm.rollsroyce.domain.school.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;

public interface SchoolRepository extends CrudRepository<School, String> {
    Page<School> findByNameContaining(String name, Pageable pageable);
}
