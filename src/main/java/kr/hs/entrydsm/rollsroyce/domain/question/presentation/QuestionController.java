package kr.hs.entrydsm.rollsroyce.domain.question.presentation;

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

import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.request.CreateQuestionRequest;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.request.UpdateQuestionRequest;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryDetailsQuestionResponse;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryQuestionListResponse;
import kr.hs.entrydsm.rollsroyce.domain.question.service.CreateQuestionService;
import kr.hs.entrydsm.rollsroyce.domain.question.service.DeleteQuestionService;
import kr.hs.entrydsm.rollsroyce.domain.question.service.QueryDetailsQuestionService;
import kr.hs.entrydsm.rollsroyce.domain.question.service.QueryMyQuestionListService;
import kr.hs.entrydsm.rollsroyce.domain.question.service.QueryQuestionListService;
import kr.hs.entrydsm.rollsroyce.domain.question.service.UpdateQuestionService;

@Tag(name = "Q&A API")
@RequiredArgsConstructor
@RequestMapping("/question")
@RestController
public class QuestionController {
    private final QueryQuestionListService queryQuestionListService;
    private final CreateQuestionService createQuestionService;
    private final UpdateQuestionService updateQuestionService;
    private final DeleteQuestionService deleteQuestionService;
    private final QueryDetailsQuestionService queryDetailsQuestionService;
    private final QueryMyQuestionListService queryMyQuestionListService;

    @Operation(summary = "질문 전체조회 API")
    @GetMapping("/all")
    public QueryQuestionListResponse getQuestionList() {
        return queryQuestionListService.execute();
    }

    @Operation(summary = "질문 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createQuestion(@RequestBody @Valid CreateQuestionRequest request) {
        createQuestionService.execute(request);
    }

    @Operation(summary = "질문 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{question-id}")
    public void updateQuestion(
            @PathVariable("question-id") Long questionId, @RequestBody @Valid UpdateQuestionRequest request) {
        updateQuestionService.execute(questionId, request);
    }

    @Operation(summary = "질문 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{question-id}")
    public void deleteQuestion(@PathVariable("question-id") Long questionId) {
        deleteQuestionService.execute(questionId);
    }

    @Operation(summary = "질문 상세조회 API")
    @GetMapping("/{question-id}")
    public QueryDetailsQuestionResponse getQuestionDetails(@PathVariable("question-id") Long questionId) {
        return queryDetailsQuestionService.execute(questionId);
    }

    @Operation(summary = "자신의 질문 전체 조회 API")
    @GetMapping()
    public QueryQuestionListResponse getMyQuestionList() {
        return queryMyQuestionListService.execute();
    }
}
