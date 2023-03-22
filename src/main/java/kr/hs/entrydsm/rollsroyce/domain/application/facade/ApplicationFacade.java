package kr.hs.entrydsm.rollsroyce.domain.application.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;

@RequiredArgsConstructor
@Component
public class ApplicationFacade {

    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;

    public void changeInformation(Long receiptCode, School school, String studentNumber, String schoolTel) {
        getGraduation(receiptCode).changeGraduationInformation(school, studentNumber, schoolTel);
    }

    public void updateInformation(User user, LocalDate graduatedAt, String educationalStatus) {
        if (EducationalStatus.QUALIFICATION_EXAM.name().equals(educationalStatus)) {
            updateQualification(user, graduatedAt);
        } else {
            updateGraduation(user, graduatedAt, educationalStatus);
        }
    }

    public void updateQualification(User user, LocalDate qualifiedAt) {
        graduationRepository.findById(user.getReceiptCode()).ifPresent(graduationRepository::delete);
        if (qualificationRepository.findById(user.getReceiptCode()).isPresent()) {
            qualificationRepository
                    .findById(user.getReceiptCode())
                    .ifPresent(qualification -> qualification.updateQualifiedAt(qualifiedAt));
        } else {
            qualificationRepository.save(new Qualification(user, qualifiedAt));
        }
    }

    public void updateGraduation(User user, LocalDate graduatedAt, String educationalStatus) {
        qualificationRepository.findById(user.getReceiptCode()).ifPresent(qualificationRepository::delete);
        if (graduationRepository.findById(user.getReceiptCode()).isPresent()) {
            graduationRepository
                    .findById(user.getReceiptCode())
                    .ifPresent(graduation -> graduation.updateInformation(
                            graduatedAt, EnumUtil.getEnum(EducationalStatus.class, educationalStatus)));
        } else {
            graduationRepository.save(
                    new Graduation(user, graduatedAt, EnumUtil.getEnum(EducationalStatus.class, educationalStatus)));
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
        return graduationRepository.findById(receiptCode).orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
    }

    public Qualification getQualification(Long receiptCode) {
        return qualificationRepository.findById(receiptCode).orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
    }

    public void deleteAll() {
        graduationRepository.deleteAll();
        qualificationRepository.deleteAll();
    }
}
