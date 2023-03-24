package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CreateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.UpdateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.CreateReplyService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.UpdateReplyService;
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

@RequiredArgsConstructor
@RequestMapping("/admin/reply")
@RestController
public class ReplyController {
    private final CreateReplyService createReplyService;
    private final UpdateReplyService updateReplyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{qna-id}")
    public void createReply(@RequestBody @Valid CreateReplyRequest request, @PathVariable("qna-id") Long qnaId) {
        createReplyService.execute(request, qnaId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{reply-id}")
    public void updateReply(@PathVariable("reply-id") Long replyId, @RequestBody @Valid UpdateReplyRequest request) {
        updateReplyService.execute(replyId, request);
    }
}
