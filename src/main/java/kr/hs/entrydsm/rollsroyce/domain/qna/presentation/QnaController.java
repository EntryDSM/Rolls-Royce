package kr.hs.entrydsm.rollsroyce.domain.qna.presentation;

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

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.CreateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.UpdateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response.QueryQnaResponse;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.CreateQnaService;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.DeleteQnaService;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.QueryQnaService;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.UpdateQnaService;

@RequiredArgsConstructor
@RequestMapping("/qna")
@RestController
public class QnaController {
    private final QueryQnaService queryQnaService;
    private final CreateQnaService createQnaService;
    private final UpdateQnaService updateQnaService;
    private final DeleteQnaService deleteQnaService;

    @GetMapping("/all")
    public QueryQnaResponse getQnaList() {
        return queryQnaService.execute();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createQna(@RequestBody @Valid CreateQnaRequest request) {
        createQnaService.execute(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{qna-id}")
    public void updateQna(@PathVariable("qna-id") Long qnaId, @RequestBody @Valid UpdateQnaRequest request) {
        updateQnaService.execute(qnaId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{qna-id}")
    public void deleteQna(@PathVariable("qna-id") Long qnaId) {
        deleteQnaService.execute(qnaId);
    }
}
