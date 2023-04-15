package kr.hs.entrydsm.rollsroyce.domain.auth.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.auth.domain.PassInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PassInfoRepository extends CrudRepository<PassInfo, String> {
        Optional<PassInfo> findByPhoneNumber(String phoneNumber);
}