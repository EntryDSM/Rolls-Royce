package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.exception.InvalidGraduateAtException;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ChangeTypeService {

    private final ApplicationFacade applicationFacade;
    private final UserFacade userFacade;

    @Transactional
    public void execute(ChangeTypeRequest request) {
        if (LocalDate.now().getYear() + 1 < request.getGraduatedAt().getYear()) {
            throw InvalidGraduateAtException.EXCEPTION;
        }

        User user = userFacade.getCurrentUser();

        applicationFacade.updateInformation(
                user, request.getGraduatedAt(), request.getEducationalStatus()
        );

        user.updateUserApplication(request);
    }

}
