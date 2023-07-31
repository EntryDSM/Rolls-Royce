package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeGraduationInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryGraduationInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryUserInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeGraduationInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.ChangeTypeService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryGraduationInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryInformationService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryTypeService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.QueryUserInfoService;
import kr.hs.entrydsm.rollsroyce.domain.application.service.UploadPhotoService;

@Tag(name = "원서 지원자 인적사항 API")
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
    private final QueryUserInfoService queryUserInfoService;

    @Operation(summary = "전형구분 선택 API")
    @PatchMapping("/type")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeType(@RequestBody @Valid ChangeTypeRequest request) {
        changeTypeService.execute(request);
    }

    @Operation(summary = "전형구분 조회 API")
    @GetMapping("/type")
    public QueryTypeResponse queryType() {
        return queryTypeService.execute();
    }

    @Operation(summary = "인적사항 조회 API")
    @GetMapping
    public QueryInformationResponse queryInformation() {
        return queryInformationService.execute();
    }

    @Operation(summary = "인적사항 입력 API")
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeInformation(@RequestBody @Valid ChangeInformationRequest request) {
        changeInformationService.execute(request);
    }

    @Operation(summary = "졸업 / 졸업예정 추가정보 입력 API")
    @PatchMapping("/graduation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeGraduationInformation(@RequestBody @Valid ChangeGraduationInformationRequest request) {
        changeGraduationInformationService.execute(request);
    }

    @Operation(summary = "졸업 / 졸업예정 인적사항 조회 API")
    @GetMapping("/graduation")
    public QueryGraduationInformationResponse queryGraduationInformation() {
        return queryGraduationInformationService.execute();
    }

    @Operation(summary = "증명사진 업로드 API")
    @PostMapping("/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhoto(@RequestPart(name = "photo") @Nullable MultipartFile file) {
        return uploadPhotoService.execute(file);
    }

    @Operation(summary = "유저 정보 조회 API")
    @GetMapping("/info")
    public QueryUserInfoResponse queryUserInfo() {
        return queryUserInfoService.execute();
    }
}
