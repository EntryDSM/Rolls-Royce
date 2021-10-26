package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    private List<ScheduleDto> schedules;

}
