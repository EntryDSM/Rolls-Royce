package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidFormatException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetNewApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.NewApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.NewApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.repository.SchoolRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.GraduationCaseVo;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.ScoreVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NewApplicantsService {

    private final EntryInfoRepository entryInfoRepository;
    private final GraduationRepository graduationRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final ScoreRepository scoreRepository;

    private final SchoolRepository schoolRepository;

    public NewApplicantsResponse execute(GetNewApplicantsRequest request) {

        List<Graduation> graduationList = graduationRepository.findByGraduatedAtAndStudentNumber(
                request.getGraduatedAt(),
                request.getStudentNumber()
        );

        List<School> schoolList = schoolRepository.findByName(request.getSchoolName());

        if (graduationList.isEmpty() || schoolList.isEmpty()) {
            throw InvalidFormatException.EXCEPTION;
        }

        return NewApplicantsResponse.builder()
                .applicants((List<NewApplicantsResponse.ApplicantDto>) getApplicants(request))
                .graduations(
                        graduationList.stream().map(
                                graduation -> NewApplicantsResponse.GraduationDto.builder()
                                        .graduatedAt(graduation.getGraduatedAt())
                                        .studentNumber(graduation.getStudentNumber())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .graduationCases((List<NewApplicantsResponse.GraduationCaseDto>) getGraduationCase(request))
                .schools(
                        schoolList.stream().map(
                                school -> NewApplicantsResponse.SchoolDto.builder()
                                        .schoolName(school.getName())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .scores((List<NewApplicantsResponse.ScoreDto>) getScore(request))
                .build();
    }

    private NewApplicantsResponse getApplicants(GetNewApplicantsRequest request) {

        List<NewApplicantVo> newApplicantList = entryInfoRepository.findByNewApplicants(
                request.getReceiptCode(),
                request.getEducationalStatus(),
                request.getApplicationType(),
                request.getName(),
                request.getIsDaejeon(),
                request.getBirthday(),
                request.getTelephoneNumber(),
                request.getApplicationRemark(),
                request.getSex(),
                request.getParentTel()
        );

        if (newApplicantList.isEmpty()) {
            throw InvalidFormatException.EXCEPTION;
        }

        return NewApplicantsResponse.builder()
                .applicants(
                        newApplicantList.stream().map(
                                newApplicant -> NewApplicantsResponse.ApplicantDto.builder()
                                        .receiptCode(newApplicant.getReceiptCode())
                                        .educationalStatus(newApplicant.getEducationalStatus().toString())
                                        .applicationType(newApplicant.getApplicationType().toString())
                                        .name(newApplicant.getName())
                                        .isDaejeon(newApplicant.getIsDaejeon())
                                        .birthday(newApplicant.getBirthday().toString())
                                        .telephoneNumber(newApplicant.getTelephoneNumber())
                                        .applicationRemark(newApplicant.getApplicationRemark().toString())
                                        .sex(newApplicant.getSex().toString())
                                        .parentTel(newApplicant.getParentTel())
                                        .build()
                        ).collect(Collectors.toList())
                ).build();
    }

    private NewApplicantsResponse getGraduationCase(GetNewApplicantsRequest request) {

        List<GraduationCaseVo> graduationCaseList = graduationCaseRepository.findAllByGraduationCase(
                request.getKoreanGrade(),
                request.getSocialGrade(),
                request.getHistoryGrade(),
                request.getMathGrade(),
                request.getScienceGrade(),
                request.getTechAndHomeGrade(),
                request.getEnglishGrade(),
                request.getVolunteerTime(),
                request.getLatenessCount(),
                request.getDayAbsenceCount(),
                request.getEarlyLeaveCount(),
                request.getLectureAbsenceCount()
        );

        if (graduationCaseList.isEmpty()) {
            throw InvalidFormatException.EXCEPTION;
        }

        return NewApplicantsResponse.builder()
                .graduationCases(
                        graduationCaseList.stream().map(
                                graduationCase -> NewApplicantsResponse.GraduationCaseDto.builder()
                                        .koreanGrade(graduationCase.getKoreanGrade())
                                        .socialGrade(graduationCase.getSocialGrade())
                                        .historyGrade(graduationCase.getHistoryGrade())
                                        .mathGrade(graduationCase.getMathGrade())
                                        .scienceGrade(graduationCase.getScienceGrade())
                                        .techAndHomeGrade(graduationCase.getTechAndHomeGrade())
                                        .englishGrade(graduationCase.getEnglishGrade())
                                        .volunteerTime(graduationCase.getVolunteerTime())
                                        .latenessCount(graduationCase.getLatenessCount())
                                        .dayAbsenceCount(graduationCase.getDayAbsenceCount())
                                        .earlyLeaveCount(graduationCase.getEarlyLeaveCount())
                                        .lectureAbsenceCount(graduationCase.getLectureAbsenceCount())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    private NewApplicantsResponse getScore(GetNewApplicantsRequest request) {

        List<ScoreVo> scoreList = scoreRepository.findAllByScore(
                request.getThirdGradeScore(),
                request.getThirdBeforeScore(),
                request.getThirdBeforeBeforeScore(),
                request.getTotalGradeScore(),
                request.getVolunteerScore(),
                request.getAttendanceScore(),
                request.getTotalScore()
        );

        if (scoreList.isEmpty()) {
            throw InvalidFormatException.EXCEPTION;
        }

        return NewApplicantsResponse.builder()
                .scores(
                        scoreList.stream().map(
                                score -> NewApplicantsResponse.ScoreDto.builder()
                                        .thirdGradeScore(score.getThirdGradeScore())
                                        .thirdBeforeScore(score.getThirdBeforeScore())
                                        .thirdBeforeBeforeScore(score.getThirdBeforeBeforeScore())
                                        .totalGradeScore(score.getTotalGradeScore())
                                        .volunteerScore(score.getVolunteerScore())
                                        .attendanceScore(score.getAttendanceScore())
                                        .totalScore(score.getTotalScore())
                                        .build()
                        ).collect(Collectors.toList())
                ).build();
    }
}
