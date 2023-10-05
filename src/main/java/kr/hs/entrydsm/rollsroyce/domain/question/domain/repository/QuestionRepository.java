package kr.hs.entrydsm.rollsroyce.domain.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByOrderByIdDesc();

    List<Question> findAllByUserOrderByCreatedAtDesc(User user);

    void deleteAllByUser(User user);
}
