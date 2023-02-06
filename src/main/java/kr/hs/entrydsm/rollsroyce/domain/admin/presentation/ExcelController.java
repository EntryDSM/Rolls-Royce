package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.ApplicantsExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.NewApplicantsExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/admin/excel")
@RestController
public class ExcelController {

    private final AdmissionTicketExcelService admissionTicketExcelService;
    private final NewApplicantsExcelService newApplicantsExcelService;

	@GetMapping("/admission-ticket")
	public void createAdmissionTicket(HttpServletResponse response) {
		admissionTicketExcelService.execute(response);
	}

	@GetMapping("/applicants/new")
	public void createNewApplicantInformation(HttpServletResponse response) {
    	newApplicantsExcelService.execute(response);
	}

}
