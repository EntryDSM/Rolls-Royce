package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.GetApplicantDetailsService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.UpdateApplicantIsPaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/applicant")
@RestController
public class ApplicantController {

    private final UpdateApplicantIsPaidService updateApplicantIsPaidService;
    private final GetApplicantDetailsService getApplicantDetailsService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/paid/{receipt-code}")
    public void updateApplicantIsPaidStatus(@PathVariable("receipt-code") long receiptCode) {
        updateApplicantIsPaidService.execute(receiptCode);
    }

    @GetMapping("/{receipt-code}")
    public ApplicantDetailsResponse getApplicantDetails(@PathVariable("receipt-code") long receiptCode) {
        return getApplicantDetailsService.execute(receiptCode);
    }

}
