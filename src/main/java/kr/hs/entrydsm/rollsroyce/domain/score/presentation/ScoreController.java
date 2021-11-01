package kr.hs.entrydsm.rollsroyce.domain.score.presentation;

import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
<<<<<<< Updated upstream
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationExamResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryQualificationExamService;
=======
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryQualificationService;
>>>>>>> Stashed changes
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScoreController {

    private final QueryGraduationService queryGraduationService;
<<<<<<< Updated upstream
    private final QueryQualificationExamService queryQualificationExamService;
=======
    private final QueryQualificationService queryQualificationService;
>>>>>>> Stashed changes

    @GetMapping("/graduation")
    public QueryGraduationResponse queryGraduation() {
        return queryGraduationService.execute();
    }

<<<<<<< Updated upstream
    @GetMapping("/qualification-exam")
    public QueryQualificationExamResponse queryQualificationExam() {
        return queryQualificationExamService.execute();
=======
    @GetMapping("/qualification")
    public QueryQualificationResponse queryQualification() {
        return queryQualificationService.execute();
>>>>>>> Stashed changes
    }
}
