package kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Reply findByQnaId(Long qnaId);
}
