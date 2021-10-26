package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.RefreshToken;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
