package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.CheckPasswordLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
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

@RequiredArgsConstructor
@Service
public class DeleteAllTablesService {

    private final AdminFacade adminFacade;

    private final ScheduleFacade scheduleFacade;

    private final ApplicationFacade applicationFacade;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    private final CheckPasswordLimitRepository checkPasswordLimitRepository;

    private final ScoreRepository scoreRepository;

    private final StatusRepository statusRepository;

    private final UserRepository userRepository;

    public void execute() {
        Admin admin = adminFacade.getRootAdmin();
        if (!checkPasswordLimitRepository.existsById(admin.getId())) {
            throw AdminNotAccessibleException.EXCEPTION;
        }

        if (!scheduleFacade.getScheduleByType(Type.SECOND_ANNOUNCEMENT).isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }

        scoreRepository.deleteAll();
        graduationCaseRepository.deleteAll();
        applicationFacade.deleteAll();
        qualificationCaseRepository.deleteAll();
        statusRepository.deleteAll();
        userRepository.deleteAll();
    }
}
