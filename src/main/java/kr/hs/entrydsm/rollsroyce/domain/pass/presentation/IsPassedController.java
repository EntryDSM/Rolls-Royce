package kr.hs.entrydsm.rollsroyce.domain.pass.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response.QueryIsFirstRoundPassResponse;
import kr.hs.entrydsm.rollsroyce.domain.pass.presentation.dto.response.QueryIsSecondRoundPassResponse;
import kr.hs.entrydsm.rollsroyce.domain.pass.service.QueryIsFirstRoundPassService;
import kr.hs.entrydsm.rollsroyce.domain.pass.service.QueryIsSecondRoundPassService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pass")
public class IsPassedController {
    private final QueryIsFirstRoundPassService queryIsFirstRoundPassService;
    private final QueryIsSecondRoundPassService queryIsSecondRoundPassService;

    @GetMapping("/first-round")
    public QueryIsFirstRoundPassResponse queryIsFirstRoundPass() {
        return queryIsFirstRoundPassService.execute();
    }

    @GetMapping("/second-round")
    public QueryIsSecondRoundPassResponse queryIsSecondRoundPass() {
        return queryIsSecondRoundPassService.execute();
    }
}
