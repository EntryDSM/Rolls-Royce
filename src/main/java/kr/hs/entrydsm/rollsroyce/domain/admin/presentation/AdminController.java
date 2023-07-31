package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CreateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.UpdateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.QueryQuestionDetailAdminResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsScoreResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.CreateReplyService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteAllTablesService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteQuestionAdminService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.DeleteReplyService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.GetApplicantsService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryQuestionDetailAdminService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryStaticsCountService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryStaticsScore;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.UpdateReplyService;

@Tag(name = "어드민 API")
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
    private final QueryQuestionDetailAdminService queryQuestionDetailAdminService;

    @Operation(summary = "테이블 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/data")
    public void deleteAllTables() {
        deleteAllTablesService.execute();
    }

    @Operation(summary = "지원자 목록 확인 API")
    @GetMapping("/applicants")
    public ApplicantsResponse getApplicants(Pageable page, @ModelAttribute GetApplicantsRequest getApplicantsRequest) {
        return getApplicantsService.execute(page, getApplicantsRequest);
    }

    @Operation(summary = "지원자 접수 현황 집계 API")
    @GetMapping("/statics/count")
    public List<StaticsCountResponse> queryStaticsCount() {
        return queryStaticsCountService.execute();
    }

    @Operation(summary = "지원자 점수 현확 집계 API")
    @GetMapping("/statics/score")
    public List<StaticsScoreResponse> queryStaticsScore() {
        return queryStaticsScore.execute();
    }

    @Operation(summary = "어드민 Q&A 질문 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{question-id}")
    public void deleteQuestion(@PathVariable("question-id") Long questionId) {
        deleteQuestionAdminService.execute(questionId);
    }

    @Operation(summary = "답변 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{question-id}")
    public void createReply(
            @RequestBody @Valid CreateReplyRequest request, @PathVariable("question-id") Long questionId) {
        createReplyService.execute(request, questionId);
    }

    @Operation(summary = "어드민 질문 상세조회 API")
    @GetMapping("/{question-id}")
    public QueryQuestionDetailAdminResponse queryQuestionDetail(@PathVariable("question-id") Long questionId) {
        return queryQuestionDetailAdminService.execute(questionId);
    }

    @Operation(summary = "답변 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{reply-id}")
    public void updateReply(@PathVariable("reply-id") Long replyId, @RequestBody @Valid UpdateReplyRequest request) {
        updateReplyService.execute(replyId, request);
    }

    @Operation(summary = "답변 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{reply-id}")
    public void deletReply(@PathVariable("reply-id") Long replyId) {
        deleteReplyService.execute(replyId);
    }
}
