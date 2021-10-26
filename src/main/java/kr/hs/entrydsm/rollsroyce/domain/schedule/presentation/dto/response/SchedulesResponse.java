package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SchedulesResponse {

    private final List<ScheduleDto> schedules;

    private final String currentStatus;

}
