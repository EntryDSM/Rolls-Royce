package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
