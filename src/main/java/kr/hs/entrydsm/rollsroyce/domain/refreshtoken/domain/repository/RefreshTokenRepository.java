package kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
}
