package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.exception.EducationalStatusUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeGraduationInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.facade.SchoolFacade;

@RequiredArgsConstructor
@Service
public class ChangeGraduationInformationService {

    private final ApplicationFacade applicationFacade;

    private final SchoolFacade schoolFacade;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute(ChangeGraduationInformationRequest request) {
        School school = schoolFacade.getSchoolByCode(request.getSchoolCode());
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        if (entryInfo.isQualification()) throw EducationalStatusUnmatchedException.EXCEPTION;

        applicationFacade.changeInformation(
                entryInfo.getReceiptCode(), school, request.getStudentNumber(), request.getSchoolTel());
    }
}
