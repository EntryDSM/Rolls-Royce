package kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findAllByOrderByIdDesc();

    List<Qna> findAllByUser(User user);
}
