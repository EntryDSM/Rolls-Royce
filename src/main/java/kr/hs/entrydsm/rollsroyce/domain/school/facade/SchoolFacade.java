package kr.hs.entrydsm.rollsroyce.domain.school.facade;

import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.repository.SchoolRepository;
import kr.hs.entrydsm.rollsroyce.domain.school.exception.SchoolNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolFacade {

	private final SchoolRepository schoolRepository;

	public School getSchoolByCode(String code) {
		return schoolRepository.findById(code)
				.orElseThrow(() -> SchoolNotFoundException.EXCEPTION);
	}

}
