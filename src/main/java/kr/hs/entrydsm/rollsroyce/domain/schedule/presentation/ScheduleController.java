package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.response.SchedulesResponse;
import kr.hs.entrydsm.rollsroyce.domain.schedule.service.GetSchedulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final GetSchedulesService getSchedulesService;

    @GetMapping("/schedules")
    public SchedulesResponse getSchedule() {
        return getSchedulesService.execute();
    }

}
