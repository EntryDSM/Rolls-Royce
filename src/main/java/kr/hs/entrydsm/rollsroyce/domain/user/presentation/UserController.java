package kr.hs.entrydsm.rollsroyce.domain.user.presentation;

import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.*;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response.StatusResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.service.*;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserLoginService loginService;
    private final UserStatusService statusService;
    private final UserSignupService signupService;
    private final SendAuthCodeService sendAuthCodeService;
    private final VerifyAuthCodeService verifyAuthCodeService;
    private final ChangePasswordService changePasswordService;
    private final UserTokenRefreshService userTokenRefreshService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signup(@RequestBody @Valid SignupRequest request) {
        return signupService.execute(request);
    }

    @PostMapping("/auth")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @PutMapping("/auth")
    public TokenResponse tokenRefresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return userTokenRefreshService.execute(refreshToken);
    }

    @PostMapping("/email/verify")
    public void sendEmail(@RequestBody @Valid SendEmailRequest request) {
        sendAuthCodeService.execute(request);
    }

    @PutMapping("/email/verify")
    public void verifyEmail(@RequestBody @Valid VerifyAuthCodeRequest request) {
        verifyAuthCodeService.execute(request);
    }

    @PutMapping("/password")
    public void changePassword(PasswordRequest request) {
        changePasswordService.execute(request);
    }

    @GetMapping("/status")
    public StatusResponse getStatus() {
        return statusService.execute();
    }

}
