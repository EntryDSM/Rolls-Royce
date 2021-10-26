package kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.Schedule;

import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAllBy();
    Schedule findByType(Type type);
}
