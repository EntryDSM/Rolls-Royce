package kr.hs.entrydsm.rollsroyce.domain.qna.presentation;

import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.CreateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.qna.service.CreateQnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/qna")
@RestController
public class QnaController {
    private final CreateQnaService createQnaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createQna(@RequestBody @Valid CreateQnaRequest request) {
        createQnaService.execute(request);
    }
}
