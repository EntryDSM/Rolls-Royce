package kr.hs.entrydsm.rollsroyce.domain.score.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.GradeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryGraduationService {

    private final UserFacade userFacade;

    private final GraduationCaseRepository graduationCaseRepository;

    public QueryGraduationResponse execute() {
        return new QueryGraduationResponse(graduationCaseRepository
                .findById(userFacade.getCurrentReceiptCode())
                .orElseThrow(() -> GradeNotFoundException.EXCEPTION));
    }
}
