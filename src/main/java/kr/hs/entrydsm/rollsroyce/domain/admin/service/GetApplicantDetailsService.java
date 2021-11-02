package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotAccessibleException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.util.ImageUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetApplicantDetailsService {

    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;
    private final ScoreRepository scoreRepository;

    private final AdminFacade adminFacade;
    private final AdminAuthenticationFacade authenticationFacade;
    private final UserFacade userFacade;
    private final S3Util s3Util;

    public ApplicantDetailsResponse execute(long receiptCode) {
        if (adminFacade.getAdminRole(authenticationFacade.getEmail()).equals(Role.ROLE_CONFIRM_FEE)) {
            throw AdminNotAccessibleException.EXCEPTION;
        }
        User user = userFacade.getUserByCode(receiptCode);
        Status userStatus = user.getStatus();

        ApplicantDetailsResponse applicantDetailsResponse = new ApplicantDetailsResponse();
        applicantDetailsResponse.setStatus(new ApplicantDetailsResponse.Status(userStatus.getIsSubmitted(), userStatus.getIsSubmitted()));
        applicantDetailsResponse.setCommonInformation(getCommonInformation(user));

        if (userStatus.getIsSubmitted()) {
            applicantDetailsResponse.setMoreInformation(getMoreInformation(user));
            applicantDetailsResponse.setEvaluation(getEvaluation(user));
        }

        return applicantDetailsResponse;
    }

    private ApplicantDetailsResponse.CommonInformation getCommonInformation(User user) {
        return ApplicantDetailsResponse.CommonInformation.builder()
                .name(user.getName())
                .email(user.getEmail())
                .telephoneNumber(user.getTelephoneNumber())
                .parentTel(user.getParentTel())
                .schoolTel(user.getSchoolTel())
                .schoolName(user.getGraduation() != null ? user.getGraduation().getSchoolName() : null)
                .build();
    }

    private ApplicantDetailsResponse.MoreInformation getMoreInformation(User user) {
        return ApplicantDetailsResponse.MoreInformation.builder()
                .photoUrl(s3Util.generateObjectUrl(user.getPhotoFileName()))
                .birthday(user.getBirthday().toString())
                .educationStatus(user.getEducationalStatus().name())
                .applicationType(user.getApplicationType().name())
                .applicationRemark(user.getApplicationRemark() != null ? user.getApplicationRemark().name() : null)
                .address(user.getAddress())
                .detailAddress(user.getDetailAddress())
                .headCount(user.getHeadcount() != null ? user.getHeadcount().name() : null)
                .build();
    }

    private ApplicantDetailsResponse.Evaluation getEvaluation(User user) {
        long receiptCode = user.getReceiptCode();
        Score score = scoreRepository.findById(receiptCode)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        GraduationCase graduationCase = graduationCaseRepository.findById(receiptCode)
                .orElse(null);
        QualificationCase qualificationCase = qualificationCaseRepository.findById(receiptCode)
                .orElse(null);
        Integer[] graduationInfo = graduationInfo(graduationCase);

        return ApplicantDetailsResponse.Evaluation.builder()
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
