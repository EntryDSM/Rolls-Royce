package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.AdmissionTicketRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.PrintApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.PrintApplicantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/excel")
@RestController
public class ExcelController {
    private final AdmissionTicketService admissionTicketService;
    private final PrintApplicantsService printApplicantsService;

    @GetMapping("/admission-ticket")
    public void printAdmissionTicket(@ModelAttribute AdmissionTicketRequest request) {
        admissionTicketService.execute(request);
    }

    @GetMapping("/applicants/new")
    public void printApplicantInformation(@ModelAttribute PrintApplicantsRequest request) {
        printApplicantsService.execute(request);
    }

}
