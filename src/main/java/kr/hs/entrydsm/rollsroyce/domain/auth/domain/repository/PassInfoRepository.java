package kr.hs.entrydsm.rollsroyce.domain.auth.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.auth.domain.PassInfo;

public interface PassInfoRepository extends CrudRepository<PassInfo, String> {
    Optional<PassInfo> findByPhoneNumber(String phoneNumber);
}
