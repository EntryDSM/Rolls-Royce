package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import org.springframework.data.repository.CrudRepository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCodeLimit;

public interface AuthCodeLimitRepository extends CrudRepository<AuthCodeLimit, String> {}
