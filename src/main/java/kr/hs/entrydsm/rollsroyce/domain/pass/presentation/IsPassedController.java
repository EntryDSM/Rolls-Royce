package kr.hs.entrydsm.rollsroyce.domain.pass.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response.QueryIsFirstRoundPassResponse;
import kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response.QueryIsSecondRoundPassResponse;
import kr.hs.entrydsm.rollsroyce.domain.pass.service.QueryIsFirstRoundPassService;
import kr.hs.entrydsm.rollsroyce.domain.pass.service.QueryIsSecondRoundPassService;

@Tag(name = "합격 여부 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/pass")
public class IsPassedController {
    private final QueryIsFirstRoundPassService queryIsFirstRoundPassService;
    private final QueryIsSecondRoundPassService queryIsSecondRoundPassService;

    @Operation(summary = "1차 합격 여부 조회 API")
    @GetMapping("/first-round")
    public QueryIsFirstRoundPassResponse queryIsFirstRoundPass() {
        return queryIsFirstRoundPassService.execute();
    }

    @Operation(summary = "2차 합격 여부 조회 API")
    @GetMapping("/second-round")
    public QueryIsSecondRoundPassResponse queryIsSecondRoundPass() {
        return queryIsSecondRoundPassService.execute();
    }
}
