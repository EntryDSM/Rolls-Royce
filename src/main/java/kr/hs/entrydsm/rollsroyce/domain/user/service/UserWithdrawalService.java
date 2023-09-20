package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Qualification;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
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

    @Transactional
    public void execute() {
        User user = userFacade.getCurrentUser();
        Score score = scoreRepository.findByReceiptCode(user.getEntryInfoReceiptCode());

        if (user.getEntryInfo() != null && EducationalStatus.GRADUATE.equals(user.getEntryInfoEducationStatus())
                || EducationalStatus.PROSPECTIVE_GRADUATE.equals(user.getEntryInfoEducationStatus())) {
            Graduation graduation = graduationRepository.findByReceiptCode(user.getEntryInfoReceiptCode());
            scoreRepository.delete(score);
            graduationRepository.delete(graduation);
            statusRepository.delete(user.getStatus());
            entryInfoRepository.delete(user.getEntryInfo());
        } else if (user.getEntryInfo() != null
                && EducationalStatus.QUALIFICATION_EXAM.equals(user.getEntryInfoEducationStatus())) {
            Qualification qualification = qualificationRepository.findByReceiptCode(user.getEntryInfoReceiptCode());
            qualificationRepository.delete(qualification);
            scoreRepository.delete(score);
            statusRepository.delete(user.getStatus());
            entryInfoRepository.delete(user.getEntryInfo());
        } else if (user.getEntryInfoReceiptCode() != null) {
            statusRepository.delete(user.getStatus());
            entryInfoRepository.delete(user.getEntryInfo());
        }

        userRepository.delete(user);
    }
}
