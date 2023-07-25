package kr.hs.entrydsm.rollsroyce.domain.screen.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.ScreenElement;
import kr.hs.entrydsm.rollsroyce.domain.screen.service.CreateScreenService;
import kr.hs.entrydsm.rollsroyce.domain.screen.service.QueryScreenListService;
import kr.hs.entrydsm.rollsroyce.domain.screen.service.UpdateScreenService;

@RequiredArgsConstructor
@RequestMapping("/screen")
@RestController
public class ScreenController {
    private final CreateScreenService createScreenService;
    private final UpdateScreenService updateScreenService;
    private final QueryScreenListService queryScreenListService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createScreen(@RequestPart(name = "file") @Nullable MultipartFile file) {
        return createScreenService.execute(file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{screen-id}")
    public void updateScreen(
            @PathVariable("screen-id") Long screenId, @RequestPart(name = "file") @Nullable MultipartFile file) {
        updateScreenService.execute(screenId, file);
    }

    @GetMapping
    public List<ScreenElement> queryScreenList() {
        return queryScreenListService.execute();
    }
}
