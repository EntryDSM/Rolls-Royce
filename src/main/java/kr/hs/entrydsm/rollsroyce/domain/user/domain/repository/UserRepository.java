package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import java.util.List;
import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByStatusIsSubmittedTrue();
    List<User> findByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
}
