package kr.hs.entrydsm.rollsroyce.domain.school.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchoolRepository extends CrudRepository<School, String> {
    Page<School> findByNameContaining(String name, Pageable pageable);

    List<School> findByName(String name);
}
