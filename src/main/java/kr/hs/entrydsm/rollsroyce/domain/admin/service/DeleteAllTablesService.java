package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationPeriodNotOverException;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.PasswordNotValidException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.repository.ScheduleRepository;
import kr.hs.entrydsm.rollsroyce.domain.schedule.domain.types.Type;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DeleteAllTablesService {

    private final ScheduleRepository scheduleRepository;

    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ScoreRepository scoreRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;
    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;

    private final AdminFacade adminFacade;

    private final PasswordEncoder passwordEncoder;

    public void execute(String password) {
        if (!scheduleRepository.findByType(Type.SECOND_ANNOUNCEMENT).getDate().isAfter(LocalDateTime.now())) {
            throw ApplicationPeriodNotOverException.EXCEPTION;
        }

        Admin admin = adminFacade.getRootAdmin();

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw PasswordNotValidException.EXCEPTION;
        }

        scoreRepository.deleteAll();
        graduationCaseRepository.deleteAll();
        graduationRepository.deleteAll();
        qualificationCaseRepository.deleteAll();
        qualificationRepository.deleteAll();
        statusRepository.deleteAll();
        userRepository.deleteAll();
    }

}
