package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.*;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryGraduationInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QuerySchoolResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeGraduationInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.*;
import kr.hs.entrydsm.rollsroyce.domain.application.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ChangeTypeService changeTypeService;
    private final ChangeInformationService changeInformationService;
    private final QueryInformationService queryInformationService;
    private final QueryTypeService queryTypeService;
    private final ChangeGraduationInformationService changeGraduationInformationService;
    private final QueryGraduationInformationService queryGraduationInformationService;
    private final ChangeIntroduceService changeIntroduceService;
    private final QueryIntroduceService queryIntroduceService;
    private final FinalSubmitService finalSubmitService;
    private final QuerySchoolService querySchoolService;
    private final ChangeStudyPlanService changeStudyPlanService;

    @PatchMapping("/users/type")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeType(@RequestBody @Valid ChangeTypeRequest request) {
        changeTypeService.execute(request);
    }

    @GetMapping("/user/type")
    public QueryTypeResponse queryType() {
        return queryTypeService.execute();
    }

    @GetMapping("/users")
    public QueryInformationResponse queryInformation() {
        return queryInformationService.execute();
    }

    @PatchMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeInformation(@RequestBody @Valid ChangeInformationRequest request) {
        changeInformationService.execute(request);
    }

    @PatchMapping("/users/graduation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeGraduationInformation(
            @RequestBody @Valid ChangeGraduationInformationRequest request) {
        changeGraduationInformationService.execute(request);
    }

    @GetMapping("/users/graduation")
    public QueryGraduationInformationResponse queryGraduationInformation() {
        return queryGraduationInformationService.execute();
    }

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
    public QuerySchoolResponse querySchool(@RequestParam("name") String name,
                                           Pageable pageable) {
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

}
