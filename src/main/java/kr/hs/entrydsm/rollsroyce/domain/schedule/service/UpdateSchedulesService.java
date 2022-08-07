package kr.hs.entrydsm.rollsroyce.domain.schedule.service;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.InvalidScheduleRequestException;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.ScheduleNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateSchedulesService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void execute(ScheduleRequest request) {
        if (request.getSchedules() == null) {
            throw InvalidScheduleRequestException.EXCEPTION;
        }

        for (ScheduleDto schedule : request.getSchedules()) {
            Schedule existedSchedule = scheduleRepository
					.findByType(Type.valueOf(schedule.getType()))
					.orElse(null);

            if (existedSchedule == null) {
                throw ScheduleNotFoundException.EXCEPTION;
            }
            existedSchedule.updateDate(schedule.getDate());
        }
    }

}
