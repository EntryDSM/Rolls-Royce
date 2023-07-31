package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request.ScheduleRequest;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.response.SchedulesResponse;
import kr.hs.entrydsm.rollsroyce.domain.schedule.service.GetSchedulesService;
import kr.hs.entrydsm.rollsroyce.domain.schedule.service.UpdateSchedulesService;

@Tag(name = "전형 일정 API")
@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    private final GetSchedulesService getSchedulesService;
    private final UpdateSchedulesService updateSchedulesService;

    @Operation(summary = "전형일정 조회 API")
    @GetMapping
    public SchedulesResponse getSchedule() {
        return getSchedulesService.execute();
    }

    @Operation(summary = "전형일정 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    public void updateSchedule(@RequestBody @Valid ScheduleRequest request) {
        updateSchedulesService.execute(request);
    }
}
