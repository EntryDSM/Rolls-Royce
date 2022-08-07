package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeGraduationInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryGraduationInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeGraduationInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeTypeService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryGraduationInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryTypeService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.UploadPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/application/users")
@RestController
public class ApplicationUserController {

	private final ChangeTypeService changeTypeService;
	private final ChangeInformationService changeInformationService;
	private final QueryInformationService queryInformationService;
	private final QueryTypeService queryTypeService;
	private final ChangeGraduationInformationService changeGraduationInformationService;
	private final QueryGraduationInformationService queryGraduationInformationService;
	private final UploadPhotoService uploadPhotoService;

	@PatchMapping("/type")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeType(@RequestBody @Valid ChangeTypeRequest request) {
		changeTypeService.execute(request);
	}

	@GetMapping("/type")
	public QueryTypeResponse queryType() {
		return queryTypeService.execute();
	}

	@GetMapping
	public QueryInformationResponse queryInformation() {
		return queryInformationService.execute();
	}

	@PatchMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeInformation(@RequestBody @Valid ChangeInformationRequest request) {
		changeInformationService.execute(request);
	}

	@PatchMapping("/graduation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeGraduationInformation(
			@RequestBody @Valid ChangeGraduationInformationRequest request) {
		changeGraduationInformationService.execute(request);
	}

	@GetMapping("/graduation")
	public QueryGraduationInformationResponse queryGraduationInformation() {
		return queryGraduationInformationService.execute();
	}

	@PostMapping("/photo")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadPhoto(@RequestPart(name = "photo") @Nullable MultipartFile file) {
		return uploadPhotoService.execute(file);
	}

}
