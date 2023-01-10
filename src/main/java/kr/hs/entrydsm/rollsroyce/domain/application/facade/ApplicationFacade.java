package kr.hs.entrydsm.rollsroyce.domain.application.facade;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class ApplicationFacade {

    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;

    public void changeInformation(Long receiptCode, School school, String studentNumber, String schoolTel) {
        getGraduation(receiptCode)
                .changeGraduationInformation(school, studentNumber, schoolTel);
    }

    public void updateInformation(EntryInfo entryInfo, LocalDate graduatedAt, String educationalStatus) {
        if (EducationalStatus.QUALIFICATION_EXAM.name().equals(educationalStatus)) {
            updateQualification(entryInfo, graduatedAt);
        } else {
            updateGraduation(entryInfo, graduatedAt, educationalStatus);
        }
    }

    public void updateQualification(EntryInfo entryInfo, LocalDate qualifiedAt) {
        graduationRepository.findById(entryInfo.getReceiptCode())
                .ifPresent(graduationRepository::delete);
        if (qualificationRepository.findById(entryInfo.getReceiptCode()).isPresent()) {
            qualificationRepository.findById(entryInfo.getReceiptCode())
                    .ifPresent(qualification -> qualification.updateQualifiedAt(qualifiedAt));
        } else {
            qualificationRepository.save(
                    new Qualification(entryInfo, qualifiedAt)
            );
        }
    }

    public void updateGraduation(EntryInfo entryInfo, LocalDate graduatedAt, String educationalStatus) {
        qualificationRepository.findById(entryInfo.getReceiptCode())
                .ifPresent(qualificationRepository::delete);
        if (graduationRepository.findById(entryInfo.getReceiptCode()).isPresent()) {
            graduationRepository.findById(entryInfo.getReceiptCode())
                    .ifPresent(graduation -> graduation.updateInformation(graduatedAt,
                            EnumUtil.getEnum(EducationalStatus.class, educationalStatus)));
        } else {
            graduationRepository.save(
                    new Graduation(entryInfo, graduatedAt,
                            EnumUtil.getEnum(EducationalStatus.class, educationalStatus))
            );
        }
    }

    public Application getApplication(Long receiptCode, EducationalStatus educationalStatus) {
        if (EducationalStatus.QUALIFICATION_EXAM.equals(educationalStatus)) {
            return getQualification(receiptCode);
        } else {
            return getGraduation(receiptCode);
        }
    }

    public Graduation getGraduation(Long receiptCode) {
        return graduationRepository.findById(receiptCode)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
    }

    public Qualification getQualification(Long receiptCode) {
        return qualificationRepository.findById(receiptCode)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
    }

    public void deleteAll() {
        graduationRepository.deleteAll();
        qualificationRepository.deleteAll();
    }

}
