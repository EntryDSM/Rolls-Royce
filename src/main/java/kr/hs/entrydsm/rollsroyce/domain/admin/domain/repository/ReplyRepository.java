package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByQuestion(Question question);
}
