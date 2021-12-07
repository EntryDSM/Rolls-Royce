package kr.hs.entrydsm.rollsroyce.domain.application.facade;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.ApplicationNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationFacade {

	private final GraduationRepository graduationRepository;
	private final QualificationRepository qualificationRepository;

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
