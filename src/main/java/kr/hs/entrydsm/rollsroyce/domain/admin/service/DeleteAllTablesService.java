package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.facade.ApplicationFacade;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.schedule.facade.ScheduleFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DeleteAllTablesService {

    private final ScheduleFacade scheduleFacade;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ScoreRepository scoreRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;
    private final ApplicationFacade applicationFacade;

    private final AdminFacade adminFacade;

    public void execute() {
        if (!scheduleFacade.getScheduleByType(Type.SECOND_ANNOUNCEMENT)
				.isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }

        Admin admin = adminFacade.getRootAdmin();

        scoreRepository.deleteAll();
        graduationCaseRepository.deleteAll();
        applicationFacade.deleteAll();
        qualificationCaseRepository.deleteAll();
        statusRepository.deleteAll();
        userRepository.deleteAll();
    }

}
