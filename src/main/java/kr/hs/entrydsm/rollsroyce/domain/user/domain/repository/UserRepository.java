package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

public interface UserRepository extends CrudRepository<User, Long>, UserCustomRepository {
    Optional<User> findByEmail(String email);

    List<User> findByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
}
