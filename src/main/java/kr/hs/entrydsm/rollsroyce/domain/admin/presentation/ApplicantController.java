package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.GetApplicantDetailsService;

@Tag(name = "어드민 입학 원서 지원자 API")
@RequiredArgsConstructor
@RequestMapping("/admin/applicant")
@RestController
public class ApplicantController {

    private final GetApplicantDetailsService getApplicantDetailsService;

    @Operation(summary = "지원자 세부 정보 조회 API")
    @GetMapping("/{receipt-code}")
    public ApplicantDetailsResponse getApplicantDetails(@PathVariable("receipt-code") long receiptCode) {
        return getApplicantDetailsService.execute(receiptCode);
    }
}
