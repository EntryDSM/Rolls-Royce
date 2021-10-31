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
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetApplicantDetailsService {

    private final UserRepository userRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;
    private final ScoreRepository scoreRepository;

    private final AdminFacade adminFacade;
    private final AdminAuthenticationFacade authenticationFacade;

    public ResponseEntity<ApplicantDetailsResponse> execute(long receiptCode) {
        if (adminFacade.getAdminRole(authenticationFacade.getEmail()).equals(Role.ROLE_CONFIRM_FEE)) {
            throw AdminNotAccessibleException.EXCEPTION;
        }
        User user = userRepository.findById(receiptCode)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        Status userStatus = user.getStatus();

        ApplicantDetailsResponse applicantDetailsResponse = new ApplicantDetailsResponse();
        applicantDetailsResponse.setStatus(new ApplicantDetailsResponse.Status(userStatus.getIsSubmitted(), userStatus.getIsSubmitted()));
        applicantDetailsResponse.setCommonInformation(
                ApplicantDetailsResponse.CommonInformation.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .telephoneNumber(user.getTelephoneNumber())
                        .parentTel(user.getParentTel())
                        //.schoolTel() 상의 필요
                        .schoolName(user.getGraduation() != null ? user.getGraduation().getSchoolName() : null)
                        .build()
        );

        if (!userStatus.getIsSubmitted()) {
            return new ResponseEntity<>(applicantDetailsResponse, HttpStatus.LOCKED);
        }

        Score score = scoreRepository.findById(receiptCode)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        GraduationCase graduationCase = graduationCaseRepository.findById(receiptCode)
                .orElse(null);
        QualificationCase qualificationCase = qualificationCaseRepository.findById(receiptCode)
                .orElse(null);

        applicantDetailsResponse.setMoreInformation(
                ApplicantDetailsResponse.MoreInformation.builder()
                        .photoUrl(user.getPhotoFileName()) // 수정 필요
                        .birthday(user.getBirthday().toString())
                        .educationStatus(user.getEducationalStatus().name())
                        .applicationType(user.getApplicationType().name())
                        .applicationRemark(user.getApplicationRemark() != null ? user.getApplicationRemark().name() : null)
                        .address(user.getAddress())
                        .detailAddress(user.getDetailAddress())
                        .headCount(user.getHeadcount() != null ? user.getHeadcount().name() : null)
                        .build()
        );
        applicantDetailsResponse.setEvaluation(
                ApplicantDetailsResponse.Evaluation.builder()
                        .conversionScore(score.getTotalScore())
                        .dayAbsenceCount(graduationCase != null ? graduationCase.getDayAbsenceCount() : null)
                        .lectureAbsenceCount(graduationCase != null ? graduationCase.getLectureAbsenceCount() : null)
                        .earlyLeaveCount(graduationCase != null ? graduationCase.getEarlyLeaveCount() : null)
                        .latenessCount(graduationCase != null ? graduationCase.getLatenessCount() : null)
                        .averageScore(qualificationCase != null ? qualificationCase.getAverageScore() : null)
                        .selfIntroduce(user.getSelfIntroduce())
                        .studyPlan(user.getStudyPlan())
                        .build()
        );
        return new ResponseEntity<>(applicantDetailsResponse, HttpStatus.OK);
    }

}
