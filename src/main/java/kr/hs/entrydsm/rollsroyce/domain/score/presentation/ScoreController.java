package kr.hs.entrydsm.rollsroyce.domain.score.presentation;

import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateGraduationRequest;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateQualificationRequest;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.UpdateGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryQualificationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.UpdateQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/score")
public class ScoreController {

    private final QueryGraduationService queryGraduationService;
    private final UpdateGraduationService updateGraduationService;
    private final QueryQualificationService queryQualificationService;
    private final UpdateQualificationService updateQualificationService;

    @GetMapping("/graduation")
    public QueryGraduationResponse queryGraduation() {
        return queryGraduationService.execute();
    }

    @PatchMapping("/graduation")
    public void updateGraduation(@RequestBody @Valid UpdateGraduationRequest request) {
        updateGraduationService.execute(request);
    }
  
    @GetMapping("/qualification")
    public QueryQualificationResponse queryQualification() {
        return queryQualificationService.execute();
    }

    @PatchMapping("/qualification")
    public void updateQualification(@RequestBody @Valid UpdateQualificationRequest request) {
        updateQualificationService.execute(request);
    }

}
