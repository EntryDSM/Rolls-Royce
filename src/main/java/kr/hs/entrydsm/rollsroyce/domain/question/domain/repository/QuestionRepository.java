package kr.hs.entrydsm.rollsroyce.domain.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByOrderByIdDesc();
}
