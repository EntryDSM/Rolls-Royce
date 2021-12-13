package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request.ScheduleRequest;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.response.SchedulesResponse;
import kr.hs.entrydsm.rollsroyce.domain.schedule.service.GetSchedulesService;
import kr.hs.entrydsm.rollsroyce.domain.schedule.service.UpdateSchedulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/schedules")
@RestController
public class ScheduleController {

    private final GetSchedulesService getSchedulesService;
    private final UpdateSchedulesService updateSchedulesService;

    @GetMapping
    public SchedulesResponse getSchedule() {
        return getSchedulesService.execute();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    public void updateSchedule(@RequestBody @Valid ScheduleRequest request) {
        updateSchedulesService.execute(request);
    }

}
