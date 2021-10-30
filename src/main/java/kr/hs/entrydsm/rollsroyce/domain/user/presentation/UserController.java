package kr.hs.entrydsm.rollsroyce.domain.user.presentation;

import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.VerifyAuthCodeRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.service.SendAuthCodeService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserLoginService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserSignupService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.VerifyAuthCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserLoginService loginService;
    private final UserSignupService signupService;
    private final SendAuthCodeService sendAuthCodeService;
    private final VerifyAuthCodeService verifyAuthCodeService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signup(@RequestBody @Valid SignupRequest request) {
        return signupService.execute(request);
    }

    @PostMapping("/email/verify")
    public void sendEmail(@RequestBody @Valid SendEmailRequest request) {
        sendAuthCodeService.execute(request);
    }

    @PutMapping("/email/verify")
    public void verifyEmail(@RequestBody @Valid VerifyAuthCodeRequest request) {
        verifyAuthCodeService.execute(request);
    }

    @PostMapping("/auth")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

}
