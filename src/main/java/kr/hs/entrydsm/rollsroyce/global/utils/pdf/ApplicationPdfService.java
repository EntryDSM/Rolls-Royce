package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.StatusNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.exception.EducationalStatusNullException;
import kr.hs.entrydsm.rollsroyce.global.exception.FinalSubmitRequiredException;
import kr.hs.entrydsm.rollsroyce.global.exception.ScoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationPdfService {

    private final EntryInfoFacade entryInfoFacade;
    private final ApplicationPdfGenerator applicationPdfGenerator;
    private final ScoreRepository scoreRepository;
    private final StatusRepository statusRepository;

    public byte[] getPreviewApplicationPdf() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        validatePrintableApplicant(entryInfo);

        Score calculatedScore = scoreRepository.findById(entryInfo.getReceiptCode())
                .orElseThrow(() -> ScoreNotFoundException.EXCEPTION);
        return applicationPdfGenerator.generate(entryInfo, calculatedScore);
    }

    public byte[] getFinalApplicationPdf() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        Status status = statusRepository
                .findById(entryInfoFacade.getCurrentReceiptCode())
                .orElseThrow(() -> StatusNotFoundException.EXCEPTION);
        if (Boolean.FALSE.equals(status.getIsSubmitted()))
            throw FinalSubmitRequiredException.EXCEPTION;

        validatePrintableApplicant(entryInfo);

        Score calculatedScore = scoreRepository.findById(entryInfo.getReceiptCode())
                .orElseThrow(() -> ScoreNotFoundException.EXCEPTION);
        return applicationPdfGenerator.generate(entryInfo, calculatedScore);
    }

    private void validatePrintableApplicant(EntryInfo entryInfo) {
        if (entryInfo.isEducationalStatusEmpty())
            throw EducationalStatusNullException.EXCEPTION;

        if (!scoreRepository.existsById(entryInfo.getReceiptCode()))
            throw ScoreNotFoundException.EXCEPTION;
    }

}
