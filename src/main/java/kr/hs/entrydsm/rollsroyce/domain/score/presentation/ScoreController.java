package kr.hs.entrydsm.rollsroyce.domain.score.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateGraduationRequest;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateQualificationRequest;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryGraduationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryQualificationResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryTotalScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryQualificationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.QueryTotalScoreService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.UpdateGraduationService;
import kr.hs.entrydsm.rollsroyce.domain.score.service.UpdateQualificationService;

@Tag(name = "성적 입력 API")
@RequiredArgsConstructor
@RequestMapping("/score")
@RestController
public class ScoreController {

    private final QueryGraduationService queryGraduationService;
    private final UpdateGraduationService updateGraduationService;
    private final QueryQualificationService queryQualificationService;
    private final UpdateQualificationService updateQualificationService;
    private final QueryTotalScoreService queryTotalScoreService;

    @Operation(summary = "미졸업자 / 졸업자 정보 조회 API")
    @GetMapping("/graduation")
    public QueryGraduationResponse queryGraduation() {
        return queryGraduationService.execute();
    }

    @Operation(summary = "미졸업자 / 졸업자 정보 입력 API")
    @PatchMapping("/graduation")
    public void updateGraduation(@RequestBody @Valid UpdateGraduationRequest request) {
        updateGraduationService.execute(request);
    }

    @Operation(summary = "검정고시 정보 조회 API")
    @GetMapping("/qualification")
    public QueryQualificationResponse queryQualification() {
        return queryQualificationService.execute();
    }

    @Operation(summary = "검정고시 정보 입력 API")
    @PatchMapping("/qualification")
    public void updateQualification(@RequestBody @Valid UpdateQualificationRequest request) {
        updateQualificationService.execute(request);
    }

    @Operation(summary = "성정 총점수 조회 API")
    @GetMapping("/total-score")
    public QueryTotalScoreResponse queryTotalScore() {
        return queryTotalScoreService.execute();
    }
}
