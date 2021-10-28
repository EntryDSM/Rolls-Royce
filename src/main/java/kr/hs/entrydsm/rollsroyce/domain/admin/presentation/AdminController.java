package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CheckPasswordRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String existingRefreshToken) {
        return tokenRefreshService.execute(existingRefreshToken);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/application/submitted/{receipt-code}")
    public void cancelApplicationSubmitStatus(@PathVariable("receipt-code") long receiptCode) {
        cancelApplicationSubmitService.execute(receiptCode);
    }

    @GetMapping( "/excel/applicants")
    public void createApplicantInformation(HttpServletResponse response) throws IOException {
        applicantsExcelService.execute(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/application/prints-arrived/{receipt-code}")
    public void updateApplicationPrintsArrivedStatus(@PathVariable("receipt-code") long receiptCode) {
        updateIsPrintsArrivedService.execute(receiptCode);
    }

}
