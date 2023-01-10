package kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository.vo.ApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.ApplicationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntryInfoCustomRepository {
    List<EntryInfo> findAllByStatusIsSubmittedTrue();

    Page<ApplicantVo> findAllByEntryInfo(String receiptCode, String schoolName, String name,
                                        Boolean isDaejeon, Boolean isOutOfHeadcount,
                                        boolean isCommon, boolean isMeister, boolean isSocial,
                                        Boolean isSubmitted, Pageable page);

    List<EntryInfo> queryStaticsCount(ApplicationType applicationType, boolean isDaejeon);

    List<EntryInfo> findAllDistanceByTypeAndDaejeon(ApplicationType applicationType, Boolean isDaejeon);
}
