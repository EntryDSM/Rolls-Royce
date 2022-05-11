package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CheckPasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.CheckPasswordService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.LoginService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.TokenRefreshService;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/admin/auth")
@RestController
public class AuthController {

    private final LoginService loginService;
    private final TokenRefreshService tokenRefreshService;
    private final CheckPasswordService checkPasswordService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return tokenRefreshService.execute(refreshToken);
    }

    @PostMapping("/check")
    public void checkPassword(@RequestBody CheckPasswordRequest request) {
        checkPasswordService.execute(request);
    }

}
