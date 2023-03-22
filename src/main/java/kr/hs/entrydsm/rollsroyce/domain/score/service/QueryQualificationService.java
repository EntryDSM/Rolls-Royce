package kr.hs.entrydsm.rollsroyce.domain.score.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.GradeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryQualificationService {

    private final UserFacade userFacade;

    private final QualificationCaseRepository qualificationCaseRepository;

    public QueryQualificationResponse execute() {
        return new QueryQualificationResponse(qualificationCaseRepository
                .findById(userFacade.getCurrentReceiptCode())
                .orElseThrow(() -> GradeNotFoundException.EXCEPTION));
    }
}
