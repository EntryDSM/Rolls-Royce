package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import org.springframework.data.repository.CrudRepository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;

public interface AuthCodeRepository extends CrudRepository<AuthCode, String> {}
