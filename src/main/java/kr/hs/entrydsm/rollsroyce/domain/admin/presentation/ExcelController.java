package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.PrintApplicantsService;

@Tag(name = "어드민 엑셀 API")
@RequiredArgsConstructor
@RequestMapping("/admin/excel")
@RestController
public class ExcelController {
    private final AdmissionTicketExcelService admissionTicketExcelService;
    private final PrintApplicantsService printApplicantsService;

    @Operation(summary = "수험표 출력 API")
    @GetMapping("/admission-ticket")
    public void printAdmissionTicket(HttpServletResponse response) {
        admissionTicketExcelService.execute(response);
    }

    @Operation(summary = "지원자 목록 출력 API")
    @GetMapping("/applicants/new")
    public void printApplicantInformation(HttpServletResponse response) {
        printApplicantsService.execute(response);
    }
}
