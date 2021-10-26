package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.response.SchedulesResponse;
import kr.hs.entrydsm.rollsroyce.domain.schedule.service.GetScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final GetScheduleService getScheduleService;

    @GetMapping("/schedules")
    public SchedulesResponse getSchedule() {
        return getScheduleService.execute();
    }

}
