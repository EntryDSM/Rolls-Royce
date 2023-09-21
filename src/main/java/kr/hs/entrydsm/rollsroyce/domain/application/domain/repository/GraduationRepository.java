package kr.hs.entrydsm.rollsroyce.domain.application.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;

public interface GraduationRepository extends CrudRepository<Graduation, Long> {

    List<Graduation> findByGraduatedAtAndStudentNumber(LocalDate graduatedAt, String studentNumber);

    Graduation findByReceiptCode(Long receiptCode);
}
