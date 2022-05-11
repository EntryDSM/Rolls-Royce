package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import java.util.List;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CheckPasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteAllTablesService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.GetApplicantsService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryStaticsCountService;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryStaticsScore;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final DeleteAllTablesService deleteAllTablesService;
    private final GetApplicantsService getApplicantsService;
    private final QueryStaticsCountService queryStaticsCountService;
    private final QueryStaticsScore queryStaticsScore;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/data")
    public void deleteAllTables() {
        deleteAllTablesService.execute();
    }

    @GetMapping("/applicants")
    public ApplicantsResponse getApplicants(Pageable page, @ModelAttribute GetApplicantsRequest getApplicantsRequest) {
        return getApplicantsService.execute(page, getApplicantsRequest);
    }

    @GetMapping("/statics/count")
    public List<StaticsCountResponse> queryStaticsCount() {
    	  return queryStaticsCountService.execute();
    }
  
    @GetMapping("/statics/score")
	  public List<StaticsScoreResponse> queryStaticsScore() {
    	  return queryStaticsScore.execute();
	  }

}
