package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CheckPasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.*;
import kr.hs.entrydsm.rollsroyce.global.security.util.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final DeleteAllTablesService deleteAllTablesService;
    private final CheckPasswordService checkPasswordService;
    private final LoginService loginService;
    private final TokenRefreshService tokenRefreshService;
    private final CancelApplicationSubmitService cancelApplicationSubmitService;
    private final ApplicantsExcelService applicantsExcelService;
    private final UpdateApplicantIsPaidService updateApplicantIsPaidService;
    private final UpdateIsPrintsArrivedService updateIsPrintsArrivedService;
    private final GetApplicantsService getApplicantsService;
    private final GetApplicantDetailsService getApplicantDetailsService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/data")
    public void deleteAllTables() {
        deleteAllTablesService.execute();
    }

    @GetMapping("/auth")
    public boolean checkPassword(@RequestBody @Valid CheckPasswordRequest request) {
        return checkPasswordService.execute(request.getPassword());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/auth")
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return tokenRefreshService.execute(refreshToken);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/application/submitted/{receipt-code}")
    public void cancelApplicationSubmitStatus(@PathVariable("receipt-code") long receiptCode) {
        cancelApplicationSubmitService.execute(receiptCode);
    }

    @GetMapping( "/excel/applicants")
    public void createApplicantInformation(HttpServletResponse response) {
        applicantsExcelService.execute(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/application/prints-arrived/{receipt-code}")
    public void updateApplicationPrintsArrivedStatus(@PathVariable("receipt-code") long receiptCode) {
        updateIsPrintsArrivedService.execute(receiptCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/applicant/paid/{receipt-code}")
    public void updateApplicantIsPaidStatus(@PathVariable("receipt-code") long receiptCode) {
        updateApplicantIsPaidService.execute(receiptCode);
    }

    @GetMapping( "/applicants")
    public ApplicantsResponse getApplicants(Pageable page, GetApplicantsRequest getApplicantsRequest) {
        return getApplicantsService.execute(page, getApplicantsRequest);
    }

    @GetMapping( "/applicant/{receipt-code}")
    public ApplicantDetailsResponse getApplicantDetails(@PathVariable("receipt-code") long receiptCode) {
        return getApplicantDetailsService.execute(receiptCode);
    }

}
