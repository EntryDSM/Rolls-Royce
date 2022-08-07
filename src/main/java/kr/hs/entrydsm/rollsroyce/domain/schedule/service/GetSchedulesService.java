package kr.hs.entrydsm.rollsroyce.domain.schedule.service;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.response.SchedulesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetSchedulesService {

    private final ScheduleFacade scheduleFacade;
    private final ScheduleRepository scheduleRepository;

    public SchedulesResponse execute() {
        return SchedulesResponse.builder()
                .schedules(
                        scheduleRepository.findAllBy().stream()
                                .map(schedule -> new ScheduleDto(schedule.getType().toString(), schedule.getDate())
                                ).collect(Collectors.toList())
                )
                .currentStatus(getCurrentStatus())
                .build();
    }

    private String getCurrentStatus() {
        LocalDateTime now = LocalDateTime.now();
        Schedule firstAnnounce = scheduleFacade.getScheduleByType(Type.FIRST_ANNOUNCEMENT);
        Schedule interview = scheduleFacade.getScheduleByType((Type.INTERVIEW));
        Schedule secondAnnounce = scheduleFacade.getScheduleByType((Type.SECOND_ANNOUNCEMENT));

        if (now.isBefore(scheduleFacade.getScheduleByType(Type.START_DATE).getDate())) return "NOT_APPLICATION_PERIOD";
        else if (!now.isAfter(scheduleFacade.getScheduleByType(Type.END_DATE).getDate())) return "APPLICATION_PERIOD";
        else if (now.isBefore(firstAnnounce.getDate())) return "BEFORE_FIRST_ANNOUNCEMENT";
        else if (now.isEqual(firstAnnounce.getDate())) return firstAnnounce.getType().toString();
        else if (now.isBefore(interview.getDate())) return "BEFORE_INTERVIEW";
        else if (now.isEqual(interview.getDate())) return interview.getType().toString();
        else if (now.isBefore(secondAnnounce.getDate())) return "BEFORE_SECOND_ANNOUNCEMENT";
        else if (now.isEqual(secondAnnounce.getDate())) return secondAnnounce.getType().toString();
        else return "END";
    }

}
