package kr.hs.entrydsm.rollsroyce.domain.screen.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.request.CreateScreenRequest;
import kr.hs.entrydsm.rollsroyce.domain.screen.service.CreateScreenService;

@RequiredArgsConstructor
@RequestMapping("/screen")
@RestController
public class ScreenController {
    private final CreateScreenService createScreenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createScreen(@RequestBody @Valid CreateScreenRequest request) {
        return createScreenService.execute(request);
    }
}
