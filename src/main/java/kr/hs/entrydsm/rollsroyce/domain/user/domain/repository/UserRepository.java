package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByTelephoneNumber(String telephoneNumber);

    boolean existsByTelephoneNumber(String telephoneNumber);

    User findByTelephoneNumberAndName(String telephoneNumber, String name);
}
