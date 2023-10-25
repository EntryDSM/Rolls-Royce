package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;

public interface EntryInfoCustomRepository {
    List<ApplicantInfoVo> findApplicationInfoListByStatusIsSubmittedTrue();

    List<ApplicantCodeVo> findApplicantCodesByIsFirstRoundPass();

    Page<ApplicantVo> findAllByEntryInfo(
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

    List<EntryInfo> queryStaticsCount(ApplicationType applicationType, boolean isDaejeon);

    List<EntryInfo> findAllDistanceByTypeAndDaejeon(ApplicationType applicationType, Boolean isDaejeon);

    List<AdmissionTicketVo> findByAdmissionTicket(
            String photoFileName,
            String receiptCode,
            String name,
            String schoolName,
            ApplicationType applicationType,
            Boolean isDaejeon,
            String examCode);

    List<NewApplicantVo> findByNewApplicants(
            String receiptCode,
            EducationalStatus educationalStatus,
            ApplicationType applicationType,
            String name,
            Boolean isDaejeon,
            LocalDate birthday,
            String telephoneNumber,
            ApplicationRemark applicationRemark,
            Sex sex,
            String parentTel);
}
