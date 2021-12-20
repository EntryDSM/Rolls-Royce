package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.EducationalStatusUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeGraduationInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.facade.SchoolFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChangeGraduationInformationService {

    private final SchoolFacade schoolFacade;
    private final ApplicationFacade applicationFacade;
    private final UserFacade userFacade;

    @Transactional
    public void execute(ChangeGraduationInformationRequest request) {
        School school = schoolFacade.getSchoolByCode(request.getSchoolCode());
        User user = userFacade.getCurrentUser();

        if (user.isQualification())
            throw EducationalStatusUnmatchedException.EXCEPTION;

        applicationFacade.changeInformation(user.getReceiptCode(), school,
                request.getStudentNumber(), request.getSchoolTel());
    }

}
