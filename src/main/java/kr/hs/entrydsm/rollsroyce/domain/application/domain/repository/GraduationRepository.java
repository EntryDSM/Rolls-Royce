package kr.hs.entrydsm.rollsroyce.domain.application.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface GraduationRepository extends CrudRepository<Graduation, Long> {

    List<Graduation> findByGraduatedAtAndAndSchoolAndStudentNumber(LocalDate graduatedAt, String schoolName, String studentNumber);
}
