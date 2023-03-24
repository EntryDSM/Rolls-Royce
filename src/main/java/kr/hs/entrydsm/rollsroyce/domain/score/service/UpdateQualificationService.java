package kr.hs.entrydsm.rollsroyce.domain.score.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.ApplicationTypeUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateQualificationRequest;

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

        graduationCaseRepository.findById(receiptCode).ifPresent(graduationCaseRepository::delete);

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
