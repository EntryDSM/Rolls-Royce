package kr.hs.entrydsm.rollsroyce.domain.score.presentation;

import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationExamResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryQualificationExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScoreController {

    private final QueryGraduationService queryGraduationService;
    private final QueryQualificationExamService queryQualificationExamService;

    @GetMapping("/graduation")
    public QueryGraduationResponse queryGraduation() {
        return queryGraduationService.execute();
    }

    @GetMapping("/qualification-exam")
    public QueryQualificationExamResponse queryQualificationExam() {
        return queryQualificationExamService.execute();
    }
}
