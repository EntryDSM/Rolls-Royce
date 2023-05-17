package kr.hs.entrydsm.rollsroyce.domain.screen.presentation;

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

import kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.request.CreateScreenRequest;
import kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.request.UpdateScreenRequest;
import kr.hs.entrydsm.rollsroyce.domain.screen.service.CreateScreenService;
import kr.hs.entrydsm.rollsroyce.domain.screen.service.UpdateScreenService;

@RequiredArgsConstructor
@RequestMapping("/screen")
@RestController
public class ScreenController {
    private final CreateScreenService createScreenService;
    private final UpdateScreenService updateScreenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createScreen(@RequestBody @Valid CreateScreenRequest request) {
        return createScreenService.execute(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/[screen-id}")
    public void updateScreen(
            @PathVariable("screen-id") Long screenId, @RequestBody @Valid UpdateScreenRequest request) {
        updateScreenService.execute(screenId, request);
    }
}
