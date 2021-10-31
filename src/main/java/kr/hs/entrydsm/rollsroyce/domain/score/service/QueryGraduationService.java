package kr.hs.entrydsm.rollsroyce.domain.score.service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.GradeNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryGraduationService {

    private final GraduationCaseRepository graduationCaseRepository;
    private final UserFacade userFacade;

    public QueryGraduationResponse execute() {
        GraduationCase graduationCase = graduationCaseRepository.findByReceiptCode(userFacade.getCurrentReceiptCode())
                .orElseThrow(GradeNotFoundException::new);
        return new QueryGraduationResponse(graduationCase);
    }
}
