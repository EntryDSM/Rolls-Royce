package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse.CommonInformation;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse.Evaluation;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse.MoreInformation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetApplicantDetailsService {

    private final S3Util s3Util;

    private final StatusFacade statusFacade;

    private final UserFacade userFacade;

    private final GraduationRepository graduationRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    private final ScoreRepository scoreRepository;
    

    public ApplicantDetailsResponse execute(long receiptCode) {
        User user = userFacade.getUserByCode(receiptCode);
        Status userStatus = statusFacade.getStatusByReceiptCode(receiptCode);

        return ApplicantDetailsResponse.builder()
                .status(new ApplicantDetailsResponse.Status(userStatus.getIsSubmitted(), userStatus.getIsSubmitted()))
                .commonInformation(getCommonInformation(user))
                .moreInformation(userStatus.getIsSubmitted() ? getMoreInformation(user) : null)
                .evaluation(userStatus.getIsSubmitted() ? getEvaluation(user) : null)
                .build();
    }

    private CommonInformation getCommonInformation(User user) {
        Graduation graduation = graduationRepository.findById(user.getReceiptCode())
                .orElse(null);
        return CommonInformation.builder()
                .name(user.getName())
                .email(user.getEmail())
                .telephoneNumber(user.getTelephoneNumber())
                .parentTel(user.getParentTel())
                .schoolTel(graduation != null ? graduation.getSchoolTel() : null)
                .schoolName(graduation != null ? graduation.getSchoolName() : null)
                .build();
    }

    private MoreInformation getMoreInformation(User user) {
        return MoreInformation.builder()
                .photoUrl(s3Util.generateObjectUrl(user.getPhotoFileName()))
                .birthday(user.getBirthday().toString())
                .educationStatus(user.getEducationalStatus().name())
                .applicationType(user.getApplicationType().name())
                .applicationRemark(user.getApplicationRemark() != null ? user.getApplicationRemark().name() : null)
                .address(user.getAddress())
                .detailAddress(user.getDetailAddress())
                .build();
    }

    private Evaluation getEvaluation(User user) {
        long receiptCode = user.getReceiptCode();
        Score score = scoreRepository.findById(receiptCode)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        GraduationCase graduationCase = graduationCaseRepository.findById(receiptCode)
                .orElse(null);
        QualificationCase qualificationCase = qualificationCaseRepository.findById(receiptCode)
                .orElse(null);
        Integer[] graduationInfo = graduationInfo(graduationCase);

        return Evaluation.builder()
                .volunteerTime(graduationCase != null ? graduationCase.getVolunteerTime() : null)
                .conversionScore(score.getTotalScore())
                .dayAbsenceCount(graduationInfo[0])
                .lectureAbsenceCount(graduationInfo[1])
                .earlyLeaveCount(graduationInfo[2])
                .latenessCount(graduationInfo[3])
                .averageScore(qualificationCase != null ? qualificationCase.getAverageScore() : null)
                .selfIntroduce(user.getSelfIntroduce())
                .studyPlan(user.getStudyPlan())
                .build();
    }

    private Integer[] graduationInfo(GraduationCase graduationCase) {
        if (graduationCase != null) {
            return new Integer[]{graduationCase.getDayAbsenceCount(), graduationCase.getLectureAbsenceCount(), graduationCase.getEarlyLeaveCount(), graduationCase.getLatenessCount()};
        } else {
            return new Integer[]{null, null, null, null};
        }
    }

}
