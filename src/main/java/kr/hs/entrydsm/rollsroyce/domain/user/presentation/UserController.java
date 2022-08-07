package kr.hs.entrydsm.rollsroyce.domain.user.presentation;

import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SendEmailRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.VerifyAuthCodeRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response.StatusResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.service.ChangePasswordService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.SendEmailAuthCodeService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.SendPasswordAuthCodeService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserLoginService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserSignupService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserStatusService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserTokenRefreshService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.VerifyAuthCodeService;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private static final String SIGN_UP_EMAIL_TEMPLATE = "RollsRoyceEmailTemplate";
    private static final String CHANGE_PASSWORD_EMAIL_TEMPLATE = "RollsRoycePasswordTemplate";

    private final UserLoginService loginService;
    private final UserStatusService statusService;
    private final UserSignupService signupService;
    private final SendEmailAuthCodeService sendEmailAuthCodeService;
    private final VerifyAuthCodeService verifyAuthCodeService;
    private final ChangePasswordService changePasswordService;
    private final UserTokenRefreshService userTokenRefreshService;
    private final SendPasswordAuthCodeService sendPasswordAuthCodeService;

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
    public void sendEmailAuthCode(@RequestBody @Valid SendEmailRequest request) {
        sendEmailAuthCodeService.execute(request, SIGN_UP_EMAIL_TEMPLATE);
    }

    @PutMapping("/email/verify")
    public void verifyEmail(@RequestBody @Valid VerifyAuthCodeRequest request) {
        verifyAuthCodeService.execute(request);
    }

    @PostMapping("/password/email/verify")
    public void sendPasswordAuthCode(@RequestBody @Valid SendEmailRequest request) {
        sendPasswordAuthCodeService.execute(request, CHANGE_PASSWORD_EMAIL_TEMPLATE);
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
