package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import org.springframework.data.repository.CrudRepository;

public interface EntryInfoRepository extends CrudRepository<EntryInfo, Long>, EntryInfoCustomRepository {
}