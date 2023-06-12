package kr.hs.entrydsm.rollsroyce.domain.auth.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response.QueryPassInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.auth.service.PassPopupService;
import kr.hs.entrydsm.rollsroyce.domain.auth.service.QueryPassInfoService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/verify")
public class PassController {
    private final QueryPassInfoService queryPassInfoService;
    private final PassPopupService passPopupService;

    @GetMapping("/info")
    public QueryPassInfoResponse getPassInfo(@RequestParam("mdl_tkn") String token) {
        return queryPassInfoService.execute(token);
    }

    @PostMapping("/popup")
    public String getPass() {
        return passPopupService.execute();
    }
}
