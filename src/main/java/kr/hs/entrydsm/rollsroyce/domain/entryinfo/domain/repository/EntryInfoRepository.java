package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

public interface EntryInfoRepository extends CrudRepository<EntryInfo, Long>, EntryInfoCustomRepository {
    Optional<EntryInfo> findByUser(User user);
}
