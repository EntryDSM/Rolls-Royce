package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.GetApplicantDetailsService;

@RequiredArgsConstructor
@RequestMapping("/admin/applicant")
@RestController
public class ApplicantController {

    private final GetApplicantDetailsService getApplicantDetailsService;

    @GetMapping("/{receipt-code}")
    public ApplicantDetailsResponse getApplicantDetails(@PathVariable("receipt-code") long receiptCode) {
        return getApplicantDetailsService.execute(receiptCode);
    }
}
