package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserInformationRepository {
    Page<User> findAllByUserInfo(String receiptCode,
                                        Boolean isDaejeon,
                                        String schoolName, String name,
                                        boolean inOfHeadcount, boolean outOfHeadcount,
                                        boolean isCommon, boolean isMeister, boolean isSocial,
                                        boolean isSubmitted,
                                        Pageable pageable);
}
