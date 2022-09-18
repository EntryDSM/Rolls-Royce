package kr.hs.entrydsm.rollsroyce.domain.schedule.service;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.InvalidScheduleRequestException;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.InvalidScheduleSequenceException;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.ScheduleNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.ScheduleDto;
import kr.hs.entrydsm.rollsroyce.domain.schedule.presentation.dto.request.ScheduleRequest;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateSchedulesService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void execute(ScheduleRequest request) {
        if (request.getSchedules() == null) {
            throw InvalidScheduleRequestException.EXCEPTION;
        }

        List<ScheduleDto> scheduleDtoList = request.getSchedules();

        for (int index = 0; index < scheduleDtoList.size(); index++) {
            ScheduleDto schedule = scheduleDtoList.get(index);

            Schedule existSchedule = scheduleRepository.
                    findByType(Type.valueOf(schedule.getType()))
                    .orElseThrow(() -> ScheduleNotFoundException.EXCEPTION);

            if (index != 0 && scheduleDtoList.get(index - 1).getDate().isAfter(schedule.getDate())) {
                throw InvalidScheduleSequenceException.EXCEPTION;
            }

            existSchedule.updateDate(schedule.getDate());
        }
    }

}
