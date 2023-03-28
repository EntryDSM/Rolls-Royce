package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeStudyPlanRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryIntroduceResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QuerySchoolResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryStudyPlanResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeIntroduceService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeStudyPlanService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.FinalSubmitService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryIntroduceService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QuerySchoolService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryStudyPlanService;

@RequiredArgsConstructor
@RequestMapping("/application")
@RestController("application.presentation.ApplicationController")
public class ApplicationController {

    private final ChangeIntroduceService changeIntroduceService;
    private final QueryIntroduceService queryIntroduceService;
    private final FinalSubmitService finalSubmitService;
    private final QuerySchoolService querySchoolService;
    private final ChangeStudyPlanService changeStudyPlanService;
    private final QueryStudyPlanService queryStudyPlanService;

    @PatchMapping("/intro")
    public void changeIntroduce(@RequestBody @Valid ChangeIntroduceRequest request) {
        changeIntroduceService.execute(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalSubmit() {
        finalSubmitService.execute();
    }

    @GetMapping("/schools")
    public QuerySchoolResponse querySchool(@RequestParam("name") String name, Pageable pageable) {
        return querySchoolService.execute(name, pageable);
    }

    @GetMapping("/intro")
    public QueryIntroduceResponse queryIntroduce() {
        return queryIntroduceService.execute();
    }

    @PatchMapping("/study-plan")
    public void changeStudyPlan(@RequestBody @Valid ChangeStudyPlanRequest request) {
        changeStudyPlanService.execute(request);
    }

    @GetMapping("/study-plan")
    public QueryStudyPlanResponse queryStudyPlan() {
        return queryStudyPlanService.execute();
    }
}
