package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Reply findByQuestion(Question question);
}
