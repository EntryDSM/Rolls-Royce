package kr.hs.entrydsm.rollsroyce.domain.schedule.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.ScheduleNotFoundException;

@RequiredArgsConstructor
@Component
public class ScheduleFacade {

    private final ScheduleRepository scheduleRepository;

    @Cacheable(value = "schedule", key = "#type", unless = "#result == null")
    public Schedule getScheduleByType(Type type) {
        return scheduleRepository.findByType(type).orElseThrow(() -> ScheduleNotFoundException.EXCEPTION);
    }
}
