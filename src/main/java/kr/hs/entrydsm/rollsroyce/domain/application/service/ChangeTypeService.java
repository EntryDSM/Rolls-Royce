package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.exception.InvalidGraduateAtException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ChangeTypeService {

    private final ApplicationFacade applicationFacade;

    private final EntryInfoFacade entryInfoFacade;

    @Transactional
    public void execute(ChangeTypeRequest request) {
        EducationalStatus applicationType = EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus());

        int now = LocalDate.now().getYear();

        if (EducationalStatus.PROSPECTIVE_GRADUATE.equals(applicationType)) {
            now++;
        }

        if (now < request.getGraduatedAt().getYear()) {
            throw InvalidGraduateAtException.EXCEPTION;
        }

        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        applicationFacade.updateInformation(
                entryInfo, request.getGraduatedAt(), request.getEducationalStatus()
        );

        entryInfo.updateUserApplication(request);
    }

}
