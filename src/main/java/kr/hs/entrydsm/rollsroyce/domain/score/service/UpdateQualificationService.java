package kr.hs.entrydsm.rollsroyce.domain.score.service;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.ApplicationTypeUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateQualificationRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateQualificationService {

    private final UserFacade userFacade;

    private final ScoreFacade scoreFacade;

    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    public void execute(UpdateQualificationRequest request) {
        User user = userFacade.getCurrentUser();
        long receiptCode = user.getReceiptCode();

        if (!user.isQualification()) {
            throw ApplicationTypeUnmatchedException.EXCEPTION;
        }

        graduationCaseRepository.findById(receiptCode)
                .ifPresent(graduationCaseRepository::delete);

        QualificationCase qualificationCase = QualificationCase.builder()
                .receiptCode(receiptCode)
                .isDaejeon(user.getIsDaejeon())
                .applicationType(user.getApplicationType())
                .educationalStatus(user.getEducationalStatus())
                .averageScore(request.getGedAverageScore())
                .build();

        qualificationCaseRepository.save(qualificationCase);

        scoreFacade.updateScore(user, qualificationCase);
    }

}
