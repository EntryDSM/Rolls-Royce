package kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.response;

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

    @Getter
    @AllArgsConstructor
    public static class ScheduleDto {

        private final String type;

        private final String date;

    }

}
