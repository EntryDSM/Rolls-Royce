package kr.hs.entrydsm.rollsroyce.domain.application.service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.EducationalStatusUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeGraduationInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.facade.SchoolFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeGraduationInformationService {

	private final SchoolFacade schoolFacade;
	private final UserFacade userFacade;

	@Transactional
	public void execute(ChangeGraduationInformationRequest request) {
		School school = schoolFacade.getSchoolByCode(request.getSchoolCode());
		User user = userFacade.getUserByCode(
				userFacade.getCurrentReceiptCode()
		);

		if(user.isQualification())
			throw EducationalStatusUnmatchedException.EXCEPTION;

		user.getGraduation().changeGraduationInformation(
				school, request.getStudentNumber());
	}

}
