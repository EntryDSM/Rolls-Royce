package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CreateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.UpdateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.CreateReplyService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteAllTablesService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteQuestionAdminService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteReplyService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.GetApplicantsService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryStaticsCountService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryStaticsScore;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.UpdateReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final DeleteAllTablesService deleteAllTablesService;
    private final GetApplicantsService getApplicantsService;
    private final QueryStaticsCountService queryStaticsCountService;
    private final QueryStaticsScore queryStaticsScore;
    private final CreateReplyService createReplyService;
    private final DeleteQuestionAdminService deleteQuestionAdminService;
    private final UpdateReplyService updateReplyService;
    private final DeleteReplyService deleteReplyService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/data")
    public void deleteAllTables() {
        deleteAllTablesService.execute();
    }

    @GetMapping("/applicants")
    public ApplicantsResponse getApplicants(Pageable page, @ModelAttribute GetApplicantsRequest getApplicantsRequest) {
        return getApplicantsService.execute(page, getApplicantsRequest);
    }

    @GetMapping("/statics/count")
    public List<StaticsCountResponse> queryStaticsCount() {
        return queryStaticsCountService.execute();
    }

    @GetMapping("/statics/score")
    public List<StaticsScoreResponse> queryStaticsScore() {
        return queryStaticsScore.execute();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{question-id}")
    public void deleteQuestion(@PathVariable("question-id") Long questionId) {
        deleteQuestionAdminService.execute(questionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{question-id}")
    public void createReply(
            @RequestBody @Valid CreateReplyRequest request, @PathVariable("question-id") Long questionId) {
        createReplyService.execute(request, questionId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{reply-id}")
    public void updateReply(@PathVariable("reply-id") Long replyId, @RequestBody @Valid UpdateReplyRequest request) {
        updateReplyService.execute(replyId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{reply-id}")
    public void deletReply(@PathVariable("reply-id") Long replyId) {
        deleteReplyService.execute(replyId);
    }
}
