package kr.hs.entrydsm.rollsroyce.domain.auth.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response.QueryPassInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.auth.service.QueryPassInfoService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/verify")
public class PassController {
    private final QueryPassInfoService queryPassInfoService;

    @GetMapping("/info")
    public QueryPassInfoResponse getPassInfo(@RequestParam("mdl_tkn") String token) {
        return queryPassInfoService.execute(token);
    }
}
