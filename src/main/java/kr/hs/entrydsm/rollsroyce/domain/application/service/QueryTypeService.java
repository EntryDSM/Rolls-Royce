package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.EducationalStatusNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryTypeService {

    private final ApplicationFacade applicationFacade;

    private final UserFacade userFacade;

    public QueryTypeResponse execute() {
        User user = userFacade
                .getCurrentUser();

        if (user.getEducationalStatus() == null)
            throw EducationalStatusNullException.EXCEPTION;

        Application application = applicationFacade
                .getApplication(user.getReceiptCode(), user.getEducationalStatus());

        return user.queryUserApplication(application);
    }

}
