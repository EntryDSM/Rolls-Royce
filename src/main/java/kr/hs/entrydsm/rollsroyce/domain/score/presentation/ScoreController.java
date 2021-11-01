package kr.hs.entrydsm.rollsroyce.domain.score.presentation;

import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScoreController {

    private final QueryGraduationService queryGraduationService;
    private final QueryQualificationService queryQualificationService;

    @GetMapping("/graduation")
    public QueryGraduationResponse queryGraduation() {
        return queryGraduationService.execute();
    }

    @GetMapping("/qualification")
    public QueryQualificationResponse queryQualification() {
        return queryQualificationService.execute();
    }
}
