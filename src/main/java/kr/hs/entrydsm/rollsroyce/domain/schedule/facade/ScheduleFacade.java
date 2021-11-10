package kr.hs.entrydsm.rollsroyce.domain.schedule.facade;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.exception.ScheduleNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleFacade {

	private final ScheduleRepository scheduleRepository;

	public Schedule getScheduleByType(Type type) {
		return scheduleRepository.findByType(type)
				.orElseThrow(() -> ScheduleNotFoundException.EXCEPTION);
	}

}
