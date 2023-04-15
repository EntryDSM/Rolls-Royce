package kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {}