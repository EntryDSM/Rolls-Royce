package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRequest {

    private List<ScheduleDto> schedules;
}
