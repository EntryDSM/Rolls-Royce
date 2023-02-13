package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetNewApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.NewApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.repository.SchoolRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.GraduationCaseRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewApplicantsService {

    private final EntryInfoRepository entryInfoRepository;
    private final GraduationRepository graduationRepository;
    private final GraduationCaseRepository graduationCaseRepository;
    private final ScoreRepository scoreRepository;

    private final SchoolRepository schoolRepository;

    public NewApplicantsResponse execute(GetNewApplicantsRequest request) {

        List<EntryInfo> newApplicantList = entryInfoRepository.findByNewApplicants(
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

        List<Graduation> graduationList = graduationRepository.findByGraduatedAtAndStudentNumber(
                request.getGraduatedAt(),
                request.getStudentNumber()
        );

        List<GraduationCase> graduationCaseList = graduationCaseRepository.findAllByGraduationCase(
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

        List<School> schoolList = schoolRepository.findByName(request.getSchoolName());

        List<Score> scoreList = scoreRepository.findAllByScore(
                request.getThirdGradeScore(),
                request.getThirdBeforeScore(),
                request.getThirdBeforeBeforeScore(),
                request.getTotalGradeScore(),
                request.getVolunteerScore(),
                request.getAttendanceScore(),
                request.getTotalScore()
        );

        return NewApplicantsResponse.builder()
                .applicants(
                        newApplicantList.stream().map(
                                newApplicant -> NewApplicantsResponse.Applicant.builder()
                                        .receiptCode(newApplicant.getReceiptCode())
                                        .educationalStatus(newApplicant.getEducationalStatus().toString())
                                        .applicationType(newApplicant.getApplicationType().toString())
                                        .name(newApplicant.getUserName())
                                        .isDaejeon(newApplicant.getIsDaejeon())
                                        .birthday(newApplicant.getBirthday().toString())
                                        .telephoneNumber(newApplicant.getUserTelephoneNumber())
                                        .applicationRemark(newApplicant.getApplicationRemark().toString())
                                        .sex(newApplicant.getSex().toString())
                                        .parentTel(newApplicant.getParentTel())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .graduations(
                        graduationList.stream().map(
                                graduation -> NewApplicantsResponse.Graduation.builder()
                                        .graduatedAt(graduation.getGraduatedAt())
                                        .studentNumber(graduation.getStudentNumber())
                                        .build()
                        ).collect(Collectors.toList()))
                .graduationCases(
                        graduationCaseList.stream().map(
                                graduationCase -> NewApplicantsResponse.GraduationCase.builder()
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
                        ).collect(Collectors.toList())
                )
                .schools(
                        schoolList.stream().map(
                                school -> NewApplicantsResponse.School.builder()
                                        .schoolName(school.getName()).build()
                        ).collect(Collectors.toList())
                )
                .scores(
                        scoreList.stream().map(
                                score -> NewApplicantsResponse.Score.builder()
                                        .thirdGradeScore(score.getThirdGradeScore())
                                        .thirdBeforeScore(score.getThirdBeforeScore())
                                        .thirdBeforeBeforeScore(score.getThirdBeforeBeforeScore())
                                        .totalGradeScore(score.getTotalGradeScore())
                                        .volunteerScore(score.getVolunteerScore())
                                        .attendanceScore(score.getAttendanceScore())
                                        .totalScore(score.getTotalScore())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
