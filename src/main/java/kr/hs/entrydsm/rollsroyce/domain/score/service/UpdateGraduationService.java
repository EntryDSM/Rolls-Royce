package kr.hs.entrydsm.rollsroyce.domain.score.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.ApplicationTypeUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateGraduationRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class UpdateGraduationService {

    private final UserFacade userFacade;

    private final ScoreFacade scoreFacade;

    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    public void execute(UpdateGraduationRequest request) {
        User user = userFacade.getCurrentUser();
        long receiptCode = user.getReceiptCode();

        if (!user.isGraduate() && !user.isProspectiveGraduate()) {
            throw ApplicationTypeUnmatchedException.EXCEPTION;
        }

        qualificationCaseRepository.findById(receiptCode).ifPresent(qualificationCaseRepository::delete);

        GraduationCase graduationCase = new GraduationCase(
                request, receiptCode, user.getIsDaejeon(), user.getApplicationType(), user.getEducationalStatus());

        graduationCaseRepository.save(graduationCase);

        scoreFacade.updateScore(user, graduationCase);
    }
}
