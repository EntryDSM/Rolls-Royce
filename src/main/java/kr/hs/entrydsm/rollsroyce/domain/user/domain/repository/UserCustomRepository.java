package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo.ApplicantVo;

public interface UserCustomRepository {
    List<User> findAllByStatusIsSubmittedTrue();

    Page<ApplicantVo> findAllByUserInfo(
            String receiptCode,
            String schoolName,
            String name,
            Boolean isDaejeon,
            Boolean isOutOfHeadcount,
            boolean isCommon,
            boolean isMeister,
            boolean isSocial,
            Boolean isSubmitted,
            Pageable page);

    boolean isAlreadyExistByEmail(String email);
}
