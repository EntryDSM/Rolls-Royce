package kr.hs.entrydsm.rollsroyce.domain.auth.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.request.PassPopupRequest;
import kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.response.QueryPassInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.auth.service.PassPopupService;
import kr.hs.entrydsm.rollsroyce.domain.auth.service.QueryPassInfoService;

@Tag(name = "PASS 인증 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/verify")
public class PassController {
    private final QueryPassInfoService queryPassInfoService;
    private final PassPopupService passPopupService;

    @Operation(summary = "PASS 인증 정보 조회 API")
    @GetMapping("/info")
    public QueryPassInfoResponse getPassInfo(@RequestParam("mdl_tkn") String token) {
        return queryPassInfoService.execute(token);
    }

    @Operation(summary = "PASS 팝업 조회 API")
    @PostMapping("/popup")
    public String getPass(@RequestBody @Valid PassPopupRequest request) {
        return passPopupService.execute(request);
    }
}
