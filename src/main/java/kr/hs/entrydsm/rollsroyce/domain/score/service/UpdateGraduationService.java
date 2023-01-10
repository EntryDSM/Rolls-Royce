package kr.hs.entrydsm.rollsroyce.domain.score.service;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.exception.ApplicationTypeUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.score.facade.ScoreFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateGraduationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateGraduationService {

    private final ScoreFacade scoreFacade;

    private final EntryInfoFacade entryInfoFacade;

    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    @Transactional
    public void execute(UpdateGraduationRequest request) {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        long receiptCode = entryInfo.getReceiptCode();

        if (!entryInfo.isGraduate() && !entryInfo.isProspectiveGraduate()) {
            throw ApplicationTypeUnmatchedException.EXCEPTION;
        }

        qualificationCaseRepository.findById(receiptCode)
                .ifPresent(qualificationCaseRepository::delete);

        GraduationCase graduationCase = new GraduationCase(request,
                receiptCode,
                entryInfo.getIsDaejeon(),
                entryInfo.getApplicationType(),
                entryInfo.getEducationalStatus()
        );

        graduationCaseRepository.save(graduationCase);

        scoreFacade.updateScore(entryInfo, graduationCase);
    }

}
