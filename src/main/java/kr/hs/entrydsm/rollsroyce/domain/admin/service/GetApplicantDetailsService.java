package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse.CommonInformation;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse.Evaluation;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantDetailsResponse.MoreInformation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.QualificationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.QualificationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetApplicantDetailsService {

    private final S3Util s3Util;

    private final StatusFacade statusFacade;

    private final EntryInfoFacade entryInfoFacade;

    private final GraduationRepository graduationRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final QualificationCaseRepository qualificationCaseRepository;

    private final ScoreRepository scoreRepository;
    

    public ApplicantDetailsResponse execute(long receiptCode) {
        EntryInfo entryInfo = entryInfoFacade.getEntryInfoByCode(receiptCode);
        Status userStatus = statusFacade.getStatusByReceiptCode(receiptCode);

        return ApplicantDetailsResponse.builder()
                .status(new ApplicantDetailsResponse.Status(userStatus.getIsPrintsArrived(), userStatus.getIsSubmitted()))
                .commonInformation(getCommonInformation(entryInfo))
                .moreInformation(userStatus.getIsSubmitted() ? getMoreInformation(entryInfo) : null)
                .evaluation(userStatus.getIsSubmitted() ? getEvaluation(entryInfo) : null)
                .build();
    }

    private CommonInformation getCommonInformation(EntryInfo entryInfo) {
        Graduation graduation = graduationRepository.findById(entryInfo.getReceiptCode())
                .orElse(null);
        return CommonInformation.builder()
                .name(entryInfo.getUserName())
                .telephoneNumber(entryInfo.getUserTelephoneNumber())
                .parentTel(entryInfo.getParentTel())
                .schoolTel(graduation != null ? graduation.getSchoolTel() : null)
                .schoolName(graduation != null ? graduation.getSchoolName() : null)
                .build();
    }

    private MoreInformation getMoreInformation(EntryInfo entryInfo) {
        return MoreInformation.builder()
                .photoUrl(s3Util.generateObjectUrl(entryInfo.getPhotoFileName()))
                .birthday(entryInfo.getBirthday().toString())
                .educationStatus(entryInfo.getEducationalStatus().name())
                .applicationType(entryInfo.getApplicationType().name())
                .applicationRemark(entryInfo.getApplicationRemark() != null ? entryInfo.getApplicationRemark().name() : null)
                .address(entryInfo.getAddress())
                .detailAddress(entryInfo.getDetailAddress())
                .build();
    }

    private Evaluation getEvaluation(EntryInfo entryInfo) {
        long receiptCode = entryInfo.getReceiptCode();
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
                .selfIntroduce(entryInfo.getSelfIntroduce())
                .studyPlan(entryInfo.getStudyPlan())
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
