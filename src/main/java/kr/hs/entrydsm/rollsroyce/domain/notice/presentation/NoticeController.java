package kr.hs.entrydsm.rollsroyce.domain.notice.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.CreateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeDetailResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.UploadNoticeImageResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.*;

@Tag(name = "공지사항 API")
@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final CreateNoticeService createNoticeService;
    private final UpdateNoticeService updateNoticeService;
    private final DeleteNoticeService deleteNoticeService;
    private final QueryNoticeListByTypeService queryNoticeListByTypeService;
    private final QueryNoticeListService queryNoticeListService;
    private final QueryNoticeDetailService queryNoticeDetailService;
    private final UploadNoticeImageService uploadNoticeImageService;

    @Operation(summary = "공지사항 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNotice(@RequestBody @Valid CreateNoticeRequest request) {
        createNoticeService.execute(request);
    }

    @Operation(summary = "공지사항 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{notice-id}")
    public String updateNotice(
            @PathVariable("notice-id") Long noticeId, @RequestBody @Valid UpdateNoticeRequest request) {
        return updateNoticeService.execute(noticeId, request);
    }

    @Operation(summary = "공지사항 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{notice-id}")
    public void deleteNotice(@PathVariable("notice-id") Long noticeId) {
        deleteNoticeService.execute(noticeId);
    }

    @Operation(summary = "공지사항 유형별 전체조회 API")
    @GetMapping(params = "type")
    public QueryNoticeResponse getNoticeListByType(@RequestParam("type") NoticeType type) {
        return queryNoticeListByTypeService.execute(type);
    }

    @Operation(summary = "공지사항 전체조회 API")
    @GetMapping
    public QueryNoticeResponse getNoticeList() {
        return queryNoticeListService.execute();
    }

    @Operation(summary = "공지사항 상세조회 API")
    @GetMapping("/{notice-id}")
    public QueryNoticeDetailResponse queryNoticeDetail(@PathVariable("notice-id") Long noticeId) {
        return queryNoticeDetailService.execute(noticeId);
    }

    @Operation(summary = "공지사항 사진 업로드 API")
    @PostMapping("/image")
    public UploadNoticeImageResponse uploadNoticeImage(@RequestPart(name = "photo") MultipartFile file) {
        return uploadNoticeImageService.execute(file);
    }
}
