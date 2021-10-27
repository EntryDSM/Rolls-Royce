package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeTypeService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryTypeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

	private final ChangeTypeService changeTypeService;
	private final ChangeInformationService changeInformationService;
	private final QueryInformationService queryInformationService;
	private final QueryTypeService queryTypeService;

	@PatchMapping("/users/type")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeType(@RequestBody @Valid ChangeTypeRequest request) {
		changeTypeService.execute(request);
	}
  
	@GetMapping("/user/type")
	public QueryTypeResponse queryType() {
		return queryTypeService.execute();
	}
  
  @GetMapping("/users")
	public QueryInformationResponse queryInformation() {
		return queryInformationService.execute();
  }

	@PatchMapping("/users")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeInformation(@RequestBody @Valid ChangeInformationRequest request) {
		changeInformationService.execute(request);
	}

}
