package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteAllTablesService {

    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ScoreRepository scoreRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;
    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;

    private final AdminFacade adminFacade;
    private final AdminAuthenticationFacade authenticationFacade;

    public void execute() {
        Admin admin = adminFacade.getRootAdmin(authenticationFacade.getEmail());
        if (admin.getRole() == Role.ROLE_ROOT) {
            scoreRepository.deleteAll();
            graduationCaseRepository.deleteAll();
            graduationRepository.deleteAll();
            qualificationCaseRepository.deleteAll();
            qualificationRepository.deleteAll();
            statusRepository.deleteAll();
            userRepository.deleteAll();
        } else {
            throw AdminNotAccessibleException.EXCEPTION;
        }
    }

}
