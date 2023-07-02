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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.CreateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeDetailResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response.QueryNoticeResponse;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.CreateNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.DeleteNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.QueryNoticeDetailService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.QueryNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.UpdateNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.UploadNoticeImageService;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final CreateNoticeService createNoticeService;
    private final UpdateNoticeService updateNoticeService;
    private final DeleteNoticeService deleteNoticeService;
    private final QueryNoticeService queryNoticeService;
    private final UploadNoticeImageService uploadNoticeImageService;
    private final QueryNoticeDetailService queryNoticeDetailService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNotice(@RequestBody @Valid CreateNoticeRequest request) {
        createNoticeService.execute(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{notice-id}")
    public void updateNotice(
            @PathVariable("notice-id") Long noticeId, @RequestBody @Valid UpdateNoticeRequest request) {
        updateNoticeService.execute(noticeId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{notice-id}")
    public void deleteNotice(@PathVariable("notice-id") Long noticeId) {
        deleteNoticeService.execute(noticeId);
    }

    @GetMapping("/all/{type}")
    public QueryNoticeResponse getNoticeList(@PathVariable("type") NoticeType type) {
        return queryNoticeService.execute(type);
    }

    @PostMapping("/{notice-id}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@PathVariable("notice-id") Long noticeId, @RequestPart MultipartFile file) {
        return uploadNoticeImageService.execute(noticeId, file);
    }

    @GetMapping("/{notice-id}")
    public QueryNoticeDetailResponse queryNoticeDetail(@PathVariable("notice-id") Long noticeId) {
        return queryNoticeDetailService.execute(noticeId);
    }
}
