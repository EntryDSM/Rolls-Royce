package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class UserWithdrawalService {
    private final UserFacade userFacade;
    private final UserRepository userRepository;
    private final EntryInfoRepository entryInfoRepository;
    private final StatusRepository statusRepository;
    private final ScoreRepository scoreRepository;
    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void execute() {
        User user = userFacade.getCurrentUser();
        Long receiptCode = user.getEntryInfoReceiptCode();
        List<Question> questions = questionRepository.findAllByUserOrderByCreatedAtDesc(user);
        if (user.getEntryInfo() != null && EducationalStatus.GRADUATE.equals(user.getEntryInfoEducationStatus())
                || EducationalStatus.PROSPECTIVE_GRADUATE.equals(user.getEntryInfoEducationStatus())) {
            scoreRepository.findById(receiptCode).ifPresent(scoreRepository::delete);
            graduationRepository.findById(receiptCode).ifPresent(graduationRepository::delete);
            statusRepository.findById(receiptCode).ifPresent(statusRepository::delete);
            entryInfoRepository.deleteById(receiptCode);
        } else if (user.getEntryInfo() != null
                && EducationalStatus.QUALIFICATION_EXAM.equals(user.getEntryInfoEducationStatus())) {
            scoreRepository.findById(receiptCode).ifPresent(scoreRepository::delete);
            qualificationRepository.findById(receiptCode).ifPresent(qualificationRepository::delete);
            statusRepository.findById(receiptCode).ifPresent(statusRepository::delete);
            entryInfoRepository.deleteById(receiptCode);
        } else if (user.getEntryInfoReceiptCode() != null) {
            statusRepository.findById(receiptCode).ifPresent(statusRepository::delete);
            entryInfoRepository.deleteById(receiptCode);
        }

        if (questions != null) {
            questionRepository.deleteAll(questions);
        }

        userRepository.delete(user);
    }
}
