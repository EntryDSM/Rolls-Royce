package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeTypeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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

	@PatchMapping("/user/type")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeType(@RequestBody @Valid ChangeTypeRequest request) {
		changeTypeService.execute(request);
	}

	@PatchMapping("/users")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeInformation(@RequestBody @Valid ChangeInformationRequest request) {
		changeInformationService.execute(request);
	}



}
