package kr.hs.entrydsm.rollsroyce.domain.application.facade;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ApplicationFacade {

	private final GraduationRepository graduationRepository;
	private final QualificationRepository qualificationRepository;

	public void updateQualification(User user, LocalDate qualifiedAt) {
		graduationRepository.findById(user.getReceiptCode())
				.ifPresent(graduationRepository::delete);
		if (qualificationRepository.findById(user.getReceiptCode()).isPresent())
			qualificationRepository.findById(user.getReceiptCode())
					.ifPresent(qualification -> qualification.updateQualifiedAt(qualifiedAt));
		else
			qualificationRepository.save(
					new Qualification(user, qualifiedAt)
			);
	}

	public void updateGraduation(User user, LocalDate graduatedAt, String educationalStatus) {
		qualificationRepository.findById(user.getReceiptCode())
				.ifPresent(qualificationRepository::delete);
		if (graduationRepository.findById(user.getReceiptCode()).isPresent())
			graduationRepository.findById(user.getReceiptCode())
					.ifPresent(graduation -> graduation.updateInformation(graduatedAt,
							EnumUtil.getEnum(EducationalStatus.class, educationalStatus)));
		else
			graduationRepository.save(
					new Graduation(user, graduatedAt,
							EnumUtil.getEnum(EducationalStatus.class, educationalStatus))
			);
	}

	public Graduation getGraduation(Long receiptCode) {
		return graduationRepository.findById(receiptCode)
				.orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
	}

	public Qualification getQualification(Long receiptCode) {
		return qualificationRepository.findById(receiptCode)
				.orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
	}

	public Graduation getGraduationOrNull(Long receiptCode) {
		return graduationRepository.findById(receiptCode)
				.orElse(null);
	}

	public Qualification getQualificationOrNull(Long receiptCode) {
		return qualificationRepository.findById(receiptCode)
				.orElse(null);
	}

	public void deleteAll() {
		graduationRepository.deleteAll();
		qualificationRepository.deleteAll();
	}

}
