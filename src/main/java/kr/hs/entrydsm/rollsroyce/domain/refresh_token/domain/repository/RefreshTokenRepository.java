package kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.RefreshToken;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
