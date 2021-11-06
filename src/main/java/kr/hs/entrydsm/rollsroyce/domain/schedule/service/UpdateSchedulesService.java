package kr.hs.entrydsm.rollsroyce.domain.schedule.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.ScheduleNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateSchedulesService {

    private final ScheduleRepository scheduleRepository;

    private final AdminFacade adminFacade;

    public void execute(ScheduleRequest request) {
        adminFacade.getRootAdmin();

        for (ScheduleDto schedule : request.getSchedules()) {
            Schedule updateSchedule = scheduleRepository.findByType(Type.valueOf(schedule.getType()));
            if (updateSchedule == null) throw ScheduleNotFoundException.EXCEPTION;
            scheduleRepository.save(updateSchedule.updateDate(schedule.getDate()));
        }
    }

}
