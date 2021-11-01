package kr.hs.entrydsm.rollsroyce.domain.score.service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.GradeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationExamResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryQualificationExamService {

    private final QualificationCaseRepository qualificationCaseRepository;
    private final UserFacade userFacade;

    public QueryQualificationExamResponse execute() {
        QualificationCase qualificationExamCase = qualificationCaseRepository.findByReceiptCode(userFacade.getCurrentReceiptCode())
                .orElseThrow(() -> GradeNotFoundException.EXCEPTION);
        return new QueryQualificationExamResponse(qualificationExamCase);
    }
}
