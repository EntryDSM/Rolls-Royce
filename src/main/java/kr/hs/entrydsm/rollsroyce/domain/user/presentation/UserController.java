package kr.hs.entrydsm.rollsroyce.domain.user.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.presentation.dto.response.StatusResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.service.CancelEntryService;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.service.CreateEntryInfoService;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.service.EntryStatusService;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.service.ChangePasswordService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserLoginService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserSignupService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserTokenRefreshService;
import kr.hs.entrydsm.rollsroyce.domain.user.service.UserWithdrawalService;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@Tag(name = "유저 API")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private static final String CHANGE_PASSWORD_EMAIL_TEMPLATE = "RollsRoycePasswordTemplate";

    private final UserLoginService loginService;
    private final UserSignupService signupService;
    private final ChangePasswordService changePasswordService;
    private final UserTokenRefreshService userTokenRefreshService;
    private final UserWithdrawalService userWithdrawalService;
    private final CreateEntryInfoService createEntryInfoService;
    private final CancelEntryService cancelEntryService;
    private final EntryStatusService entryStatusService;

    @Operation(summary = "회원가입 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signup(@RequestBody @Valid SignupRequest request) {
        return signupService.execute(request);
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/auth")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @Operation(summary = "토큰 재발급 API")
    @PutMapping("/auth")
    public TokenResponse tokenRefresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return userTokenRefreshService.execute(refreshToken);
    }

    @Operation(summary = "비밀번호 변경 API")
    @PatchMapping("/password")
    public void changePassword(@RequestBody @Valid PasswordRequest request) {
        changePasswordService.execute(request);
    }

    @Operation(summary = "회원탈퇴 API")
    @DeleteMapping
    public void withdrawal() {
        userWithdrawalService.execute();
    }

    @Operation(summary = "원서 생성 API")
    @PostMapping("/entry")
    public void createEntry() {
        createEntryInfoService.execute();
    }

    @Operation(summary = "원서 접수 최소 API")
    @DeleteMapping("/{receipt-code}")
    public void cancelEntry(@PathVariable("receipt-code") Long code) {
        cancelEntryService.execute(code);
    }

    @Operation(summary = "지원정보 상태 조회 API")
    @GetMapping("/status")
    public StatusResponse queryEntryStatus() {
        return entryStatusService.execute();
    }
}
