package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CheckPasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.CheckPasswordService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.LoginService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.TokenRefreshService;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@Tag(name = "어드민 Auth API")
@RequiredArgsConstructor
@RequestMapping("/admin/auth")
@RestController
public class AuthController {

    private final LoginService loginService;
    private final TokenRefreshService tokenRefreshService;
    private final CheckPasswordService checkPasswordService;

    @Operation(summary = "어드민 로그인 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @Operation(summary = "어드민 토큰재발급 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return tokenRefreshService.execute(refreshToken);
    }

    @Operation(summary = "어드민 비밀번호 확인 API")
    @PostMapping("/check")
    public void checkPassword(@RequestBody @Valid CheckPasswordRequest request) {
        checkPasswordService.execute(request);
    }
}
