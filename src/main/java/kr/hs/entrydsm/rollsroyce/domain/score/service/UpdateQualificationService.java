package kr.hs.entrydsm.rollsroyce.domain.score.service;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.ApplicationTypeUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateQualificationRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateQualificationService {

    private final EntryInfoFacade entryInfoFacade;

    private final ScoreFacade scoreFacade;

    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    @Transactional
    public void execute(UpdateQualificationRequest request) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        long receiptCode = entryInfo.getReceiptCode();

        if (!entryInfo.isQualification()) {
            throw ApplicationTypeUnmatchedException.EXCEPTION;
        }

        graduationCaseRepository.findById(receiptCode)
                .ifPresent(graduationCaseRepository::delete);

        QualificationCase qualificationCase = QualificationCase.builder()
                .receiptCode(receiptCode)
                .isDaejeon(entryInfo.getIsDaejeon())
                .applicationType(entryInfo.getApplicationType())
                .educationalStatus(entryInfo.getEducationalStatus())
                .averageScore(request.getGedAverageScore())
                .build();

        qualificationCaseRepository.save(qualificationCase);

        scoreFacade.updateScore(entryInfo, qualificationCase);
    }

}
