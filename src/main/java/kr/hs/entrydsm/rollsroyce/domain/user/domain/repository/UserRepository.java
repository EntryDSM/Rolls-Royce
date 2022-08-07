package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select A from tbl_user A join tbl_status B on B.receiptCode = A.receiptCode and B.isSubmitted = true")
    List<User> findAllByStatusIsSubmittedTrue();

    List<User> findByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);

    @Query("select A from tbl_user A "
            + "join tbl_status B on B.receiptCode = A.receiptCode and B.isSubmitted = true "
            + "where A.applicationType = :applicationType and A.isDaejeon = :isDaejeon")
    List<User> findByApplicationTypeAndIsDaejeonAndIsSubmittedTrue(ApplicationType applicationType, boolean isDaejeon);

    @Query(value = "select * from tbl_user u "
            + "left outer join tbl_graduation_application a on u.receipt_code = a.receipt_code "
            + "join tbl_status s on u.receipt_code = s.receipt_code "
            + "left outer join tbl_school c on a.school_code = c.code "
            + "where (u.receipt_code like ?1) and (?2 is null or c.name like ?2) and (u.name like ?3) "
            + "and (?4 is null or u.is_daejeon = ?4) "
            + "and (u.headcount = 'OUT_OF_HEADCOUNT' and ?5)"
            + "and ((u.application_type = 'COMMON' and ?6) or (u.application_type = 'MEISTER' and ?7) or (u.application_type = 'SOCIAL' and ?8)) "
            + "and (?10 is null or s.is_submitted = ?9)", nativeQuery = true)
    Page<User> findAllByUserInfo(String receiptCode, String schoolName, String name,
                                 Boolean isDaejeon,
                                 boolean outOfHeadcount,
                                 boolean isCommon, boolean isMeister, boolean isSocial,
                                 Boolean isSubmitted,
                                 Pageable pageable);
}
