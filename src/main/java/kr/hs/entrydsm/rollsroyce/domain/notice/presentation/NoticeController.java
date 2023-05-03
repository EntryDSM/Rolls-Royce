package kr.hs.entrydsm.rollsroyce.domain.notice.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.CreateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.CreateNoticeService;
import kr.hs.entrydsm.rollsroyce.domain.notice.service.UpdateNoticeService;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final CreateNoticeService createNoticeService;
    private final UpdateNoticeService updateNoticeService;

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
}
