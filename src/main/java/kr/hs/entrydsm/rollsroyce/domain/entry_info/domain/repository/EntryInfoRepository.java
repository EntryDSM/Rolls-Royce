package kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import org.springframework.data.repository.CrudRepository;


public interface EntryInfoRepository extends CrudRepository<EntryInfo, Long>, EntryInfoCustomRepository {
}
