package kr.hs.entrydsm.rollsroyce.domain.qna.presentation;

import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.CreateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.UpdateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.CreateQnaService;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.UpdateQnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/qna")
@RestController
public class QnaController {
    private final CreateQnaService createQnaService;
    private final UpdateQnaService updateQnaService;

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
}
