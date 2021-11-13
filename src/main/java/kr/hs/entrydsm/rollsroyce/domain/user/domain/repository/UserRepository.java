package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import java.util.List;
import java.util.Optional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("select A from tbl_user A join tbl_status B on B.receiptCode = A.receiptCode and B.isSubmitted = true")
    List<User> findAllByStatusIsSubmittedTrue();
    List<User> findByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
    @Query("select A from tbl_user A "
			+ "join tbl_status B on B.receiptCode = A.receiptCode and B.isSubmitted = true "
			+ "where A.applicationType = :applicationType and A.isDaejeon = :isDaejeon")
	List<User> findByApplicationTypeAndIsDaejeonAndIsSubmittedTrue(ApplicationType applicationType, boolean isDaejeon);
}
