package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.AdmissionTicketRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetNewApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.AdmissionTicketExcelService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.NewApplicantsExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public void createAdmissionTicket(@ModelAttribute AdmissionTicketRequest request) {
		admissionTicketExcelService.execute(request);
	}

	@GetMapping("/applicants/new")
	public void createNewApplicantInformation(@ModelAttribute GetNewApplicantsRequest request) {
    	newApplicantsExcelService.execute(request);
	}

}
