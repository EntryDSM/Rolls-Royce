package kr.hs.entrydsm.rollsroyce.domain.notice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.Notice;
import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByType(NoticeType type);

    Optional<Notice> findById(Long noticeId);
}
