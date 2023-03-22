package kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
}
