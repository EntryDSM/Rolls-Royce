package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.exception.StatusNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.EducationalStatusNullException;
import kr.hs.entrydsm.rollsroyce.global.exception.FinalSubmitRequiredException;
import kr.hs.entrydsm.rollsroyce.global.exception.ScoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationPdfService {

    private final UserFacade userFacade;
    private final ApplicationPdfGenerator applicationPdfGenerator;
    private final ScoreRepository scoreRepository;
    private final StatusRepository statusRepository;

    public byte[] getPreviewApplicationPdf() {
        User user = userFacade.getCurrentUser();

        validatePrintableApplicant(user);

        Score calculatedScore = scoreRepository.findById(user.getReceiptCode())
                .orElseThrow(() -> ScoreNotFoundException.EXCEPTION);
        return applicationPdfGenerator.generate(user, calculatedScore);
    }

    public byte[] getFinalApplicationPdf() {
        Status status = statusRepository
                .findById(userFacade.getCurrentReceiptCode())
                .orElseThrow(() -> StatusNotFoundException.EXCEPTION);
        if (Boolean.FALSE.equals(status.getIsSubmitted()))
            throw FinalSubmitRequiredException.EXCEPTION;

        User user = userFacade.getCurrentUser();

        validatePrintableApplicant(user);

        Score calculatedScore = scoreRepository.findById(user.getReceiptCode())
                .orElseThrow(() -> ScoreNotFoundException.EXCEPTION);
        return applicationPdfGenerator.generate(user, calculatedScore);
    }

    private void validatePrintableApplicant(User user) {
        if (user.isEducationalStatusEmpty())
            throw EducationalStatusNullException.EXCEPTION;

        if (!scoreRepository.existsById(user.getReceiptCode()))
            throw ScoreNotFoundException.EXCEPTION;
    }

}
