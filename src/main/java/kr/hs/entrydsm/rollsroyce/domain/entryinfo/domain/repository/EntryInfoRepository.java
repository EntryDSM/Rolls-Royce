package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

public interface EntryInfoRepository extends CrudRepository<EntryInfo, Long>, EntryInfoCustomRepository {
    Optional<EntryInfo> findByUser(User user);
}