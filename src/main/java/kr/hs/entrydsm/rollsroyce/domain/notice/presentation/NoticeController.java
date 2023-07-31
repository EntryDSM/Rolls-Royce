package kr.hs.entrydsm.rollsroyce.domain.notice.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.CreateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeDetailResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.CreateNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.DeleteNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.QueryNoticeDetailService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.QueryNoticeListService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.UpdateNoticeService;

@Tag(name = "공지사항 API")
@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final CreateNoticeService createNoticeService;
    private final UpdateNoticeService updateNoticeService;
    private final DeleteNoticeService deleteNoticeService;
    private final QueryNoticeListService queryNoticeListService;
    private final QueryNoticeDetailService queryNoticeDetailService;

    @Operation(summary = "공지사항 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createNotice(@RequestBody @Valid CreateNoticeRequest request) {
        return createNoticeService.execute(request);
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
    @GetMapping("/all/{type}")
    public QueryNoticeResponse getNoticeList(@PathVariable("type") NoticeType type) {
        return queryNoticeListService.execute(type);
    }

    @Operation(summary = "공지사항 상세조회 API")
    @GetMapping("/{notice-id}")
    public QueryNoticeDetailResponse queryNoticeDetail(@PathVariable("notice-id") Long noticeId) {
        return queryNoticeDetailService.execute(noticeId);
    }
}
