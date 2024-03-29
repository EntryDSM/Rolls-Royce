package kr.hs.entrydsm.rollsroyce.domain.score.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.GradeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;

@RequiredArgsConstructor
@Service
public class QueryQualificationService {

    private final EntryInfoFacade entryInfoFacade;

    private final QualificationCaseRepository qualificationCaseRepository;

    public QueryQualificationResponse execute() {
        QualificationCase qualificationCase = qualificationCaseRepository
                .findById(entryInfoFacade.getCurrentReceiptCode())
                .orElseThrow(() -> GradeNotFoundException.EXCEPTION);

        return new QueryQualificationResponse(qualificationCase);
    }
}
