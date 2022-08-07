package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.ApplicantsExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/admin/excel")
@RestController
public class ExcelController {

    private final ApplicantsExcelService applicantsExcelService;
    private final AdmissionTicketExcelService admissionTicketExcelService;

    @GetMapping("/applicants")
    public void createApplicantInformation(HttpServletResponse response) {
        applicantsExcelService.execute(response);
    }

	@GetMapping("/admission-ticket")
	public void createAdmissionTicket(HttpServletResponse response) {
		admissionTicketExcelService.execute(response);
	}

}
