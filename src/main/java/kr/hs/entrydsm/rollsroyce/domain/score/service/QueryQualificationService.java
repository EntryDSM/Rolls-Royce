package kr.hs.entrydsm.rollsroyce.domain.score.service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.GradeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryQualificationService {

    private final QualificationCaseRepository qualificationCaseRepository;
    private final UserFacade userFacade;

    public QueryQualificationResponse execute() {
        return new QueryQualificationResponse(qualificationCaseRepository.findById(userFacade.getCurrentReceiptCode())
                .orElseThrow(() -> GradeNotFoundException.EXCEPTION));
    }
}
