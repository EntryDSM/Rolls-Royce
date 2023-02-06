package kr.hs.entrydsm.rollsroyce.domain.user.presentation;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.service.CancelEntryService;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.service.CreateEntryService;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.service.EntryStatusService;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.service.ChangePasswordService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserLoginService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserSignupService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserTokenRefreshService;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private static final String CHANGE_PASSWORD_EMAIL_TEMPLATE = "RollsRoycePasswordTemplate";

    private final UserLoginService loginService;
    private final UserSignupService signupService;
    private final ChangePasswordService changePasswordService;
    private final UserTokenRefreshService userTokenRefreshService;
    private final CreateEntryService createEntryService;
    private final CancelEntryService cancelEntryService;
    private final EntryStatusService entryStatusService;

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

    @PutMapping("/password")
    public void changePassword(@RequestBody @Valid PasswordRequest request) {
        changePasswordService.execute(request);
    }

    @PostMapping("/entry")
    public void createEntry() {
        createEntryService.execute();
    }

    @DeleteMapping("/{receipt-code}")
    public void cancelEntry(@PathVariable("receipt-code") Long code) {
        cancelEntryService.execute(code);
    }

    @GetMapping("/status")
    public void queryEntryStatus() {
        entryStatusService.execute();
    }

}
