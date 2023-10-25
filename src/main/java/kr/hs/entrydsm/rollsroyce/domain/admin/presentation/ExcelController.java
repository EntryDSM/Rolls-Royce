package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.PrintApplicantCodesService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.ApplicationInformationExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.CheckApplicantsService;

@Tag(name = "어드민 엑셀 API")
@RequiredArgsConstructor
@RequestMapping("/admin/excel")
@RestController
public class ExcelController {
    private final AdmissionTicketExcelService admissionTicketExcelService;
    private final CheckApplicantsService checkApplicantsService;
    private final ApplicationInformationExcelService applicationInformationExcelService;
    private final PrintApplicantCodesService printApplicantCodesService;

    @Operation(summary = "수험표 엑셀 출력 API")
    @GetMapping("/admission-ticket")
    public void printAdmissionTicket(HttpServletResponse response) {
        admissionTicketExcelService.execute(response);
    }

    @Operation(summary = "지원자 목록 검증 엑셀 출력 API")
    @GetMapping("/applicants/check")
    public void checkApplicantInformation(HttpServletResponse response) {
        checkApplicantsService.execute(response);
    }

    @Operation(summary = "지원자 목록 엑셀 출력 API")
    @GetMapping("/applicants")
    public void printApplicantInformation(HttpServletResponse response) {
        applicationInformationExcelService.execute(response);
    }

    @Operation(summary = "지원자 코드 목록 엑셀 출력 API")
    @GetMapping("/applicants/code")
    public void printApplicantCode(HttpServletResponse response) {
        printApplicantCodesService.execute(response);
    }
}
