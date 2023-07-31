package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.AdmissionTicketRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.PrintApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.PrintApplicantsService;

@Tag(name = "어드민 엑셀 API")
@RequiredArgsConstructor
@RequestMapping("/admin/excel")
@RestController
public class ExcelController {
    private final AdmissionTicketService admissionTicketService;
    private final PrintApplicantsService printApplicantsService;

    @Operation(summary = "수험표 출력 API")
    @GetMapping("/admission-ticket")
    public void printAdmissionTicket(@ModelAttribute AdmissionTicketRequest request) {
        admissionTicketService.execute(request);
    }

    @Operation(summary = "지원자 목록 출력 API")
    @GetMapping("/applicants/new")
    public void printApplicantInformation(@ModelAttribute PrintApplicantsRequest request) {
        printApplicantsService.execute(request);
    }
}
